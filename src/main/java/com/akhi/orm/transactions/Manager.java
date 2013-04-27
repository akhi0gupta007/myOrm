package com.akhi.orm.transactions;

import com.akhi.orm.connections.Commands;
import com.akhi.orm.connections.ConnectionPool;
import com.akhi.orm.connections.ConnectionPoolQueueElement;

import java.sql.Connection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Manager implements Runnable {

	final static String VALID_TOKEN = "akhi";

	private ORMSession session = null;

	private BlockingQueue<ConnectionPoolQueueElement> queue = new LinkedBlockingDeque<ConnectionPoolQueueElement>();

	private ConnectionPool connectionPool = new ConnectionPool(
			"jdbc:mysql://localhost:3306/notifier", "root", "9868");

	public Manager() {
		Thread main = new Thread(this);
		main.start();
	}

	public ORMSession getSession() {
		ConnectionPoolQueueElement elm = new ConnectionPoolQueueElement(
				VALID_TOKEN, Commands.PROVISION);
		queue.offer(elm);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Session Request" + session);
		return this.session;

	}

	public void closeSession(ORMSession session) {
		ConnectionPoolQueueElement elm = new ConnectionPoolQueueElement(
				VALID_TOKEN, Commands.RETURN);
		elm.setConnection(session.closeSession());
		queue.offer(elm);

	}

	public ConnectionPool getConnectionPool() {
		return connectionPool;
	}

	public void run() {

		while (true) {

			try {
				ConnectionPoolQueueElement elm = queue.take();
				if (elm.getToken() != null
						&& elm.getToken().equals(VALID_TOKEN)) {

					switch (elm.getCmd()) {
					case PROVISION:
						Connection con = connectionPool.provisionConnection();
						ORMSession session = new SessionImpl();
						session.registerConnection(con);
						this.setSession(session);
						break;
					case RETURN:
						Connection con1 = elm.getConnection();
						if (con1 != null) {
							connectionPool.returnConnectionToPool(con1);
						}
						break;
					default:
						break;
					}

				} else {
					continue;
				}

			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}

	}

	public BlockingQueue<ConnectionPoolQueueElement> getQueue() {
		return queue;
	}

	public void setQueue(BlockingQueue<ConnectionPoolQueueElement> queue) {
		this.queue = queue;
	}

	public void setSession(ORMSession session) {
		this.session = session;
	}

}

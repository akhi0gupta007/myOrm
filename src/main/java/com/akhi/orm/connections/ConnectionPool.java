package com.akhi.orm.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;


public class ConnectionPool {

	private final String connectionUrl;
	private final String userName;
	private final String userPassword;
	private final static int MAX_CONNECTION = 2;



	private Vector<Connection> connectionsAvailable = new Vector<Connection>();

	public ConnectionPool(String connectionUrl, String userName,
			String userPassword) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("ConnectionPool : Drivers Loaded Sucessfully ");
		} catch (Exception E) {
			System.out.println("Unable to load a driver " + E.toString());
		}
		this.connectionUrl = connectionUrl;
		this.userName = userName;
		this.userPassword = userPassword;

		for (int count = 0; count < MAX_CONNECTION; count++) {
			try {
				connectionsAvailable.addElement(getConnection());
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		System.out.println(connectionsAvailable.size());
	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(connectionUrl, userName,
				userPassword);
	}

	public synchronized void returnConnectionToPool(Connection connection) {

		connectionsAvailable.addElement(connection);
	}

	public synchronized Connection provisionConnection() {
		Connection connection = null;

		if (connectionsAvailable.size() > 0) {
			System.out.println("Connection is available");
			connection = connectionsAvailable.firstElement();
			connectionsAvailable.removeElementAt(0);
		}

		return connection;

	}

	public String getUserName() {
		return userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public static void main(String[] args) throws SQLException,
			InterruptedException {
		ConnectionPool pool = new ConnectionPool(
				"jdbc:mysql://localhost:3306/notifier", "root", "9868");

		Connection con = pool.provisionConnection();
		System.out.println(con);
		Connection con1 = pool.provisionConnection();
		System.out.println(con1);

		System.out.println(pool.provisionConnection());

		System.out.println(pool.provisionConnection());
		pool.returnConnectionToPool(con1);
		System.out.println(pool.provisionConnection());
	}

	public String getConnectionUrl() {
		return connectionUrl;
	}

	public Vector<?> getConnectionsAvailable() {
		return connectionsAvailable;
	}

	public void setConnectionsAvailable(Vector<Connection> connectionsAvailable) {
		this.connectionsAvailable = connectionsAvailable;
	}

}

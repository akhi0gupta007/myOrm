package com.akhi.orm.connections;

import java.sql.Connection;

public class ConnectionPoolQueueElement {
	private String token;
	private Commands cmd;
	private Connection connection;
	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	public ConnectionPoolQueueElement(String token, Commands cmd) {
		super();
		this.token = token;
		this.cmd = cmd;
	}

	public Commands getCmd() {
		return cmd;
	}

	public void setCmd(Commands cmd) {
		this.cmd = cmd;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}

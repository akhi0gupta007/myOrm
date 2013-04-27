package com.akhi.orm.transactions;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.akhi.orm.common.Employee;

public class SessionImpl implements ORMSession {

	private Connection con;

	public void save(Object object) throws Exception {

		Class<? extends Object> reflectClass = object.getClass();

		String table = reflectClass.getSimpleName();
		Map<String, String> map = new HashMap<String, String>();

		Field[] fields = reflectClass.getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {

			fields[i].setAccessible(true);
			String fieldName = fields[i].getName();
			String captalised = fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
			System.out.println(captalised);
			String methodName = "get" + captalised;
			System.out.println(methodName);

			Method method = reflectClass.getDeclaredMethod(methodName, null);
			Object obj = method.invoke(object, null);
			map.put(fieldName, obj.toString());

		}

		persist(table.toLowerCase(), map);

	}

	private void persist(String table, Map<String, String> map) {
		System.out.println("Persist " + table + " Map" + map);

		StringBuffer buf = new StringBuffer();
		buf.append(table + "(");

		for (Map.Entry<String, String> entry : map.entrySet()) {
			buf.append(entry.getKey());
			buf.append(",");
		}

		buf.deleteCharAt(buf.length() - 1);
		buf.append(")");
		System.out.println(buf);

		StringBuffer buf2 = new StringBuffer();
		buf2.append("values(");
		for (Map.Entry<String, String> entry : map.entrySet()) {

			buf2.append("\"" + entry.getValue() + "\"");
			buf2.append(",");
		}

		buf2.deleteCharAt(buf2.length() - 1);
		buf2.append(")");
		System.out.println(buf2);
		String query = "insert into " + buf.toString() + " " + buf2.toString();
		System.out.println(query);
		try {
			executeQuery(query);
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	private void executeQuery(String query) throws SQLException {
		PreparedStatement preparedStatement = null;
		if (con != null) {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.executeUpdate();
			System.out.println("Record is inserted into table!");
		} else {
			System.err.println("No Connection Available");
		}
	}

	public void registerConnection(Connection con) {
		this.con = con;

	}

	public Connection getCon() {
		return con;
	}

	public Connection closeSession() {

		return this.getCon();
	}

	public static void main(String[] args) throws Exception {

		Employee emp = new Employee("Akhilesh", "Developer");
		SessionImpl session = new SessionImpl();
		session.save(emp);

	}

}

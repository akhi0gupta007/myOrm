package com.akhi.orm.transactions;

import java.sql.Connection;

public interface ORMSession {
	void save(Object object) throws Exception;

	void registerConnection(Connection con);

	Connection closeSession();
}

package de.telekom.sea7.impl;

import java.sql.Connection;
import java.sql.SQLException;

public interface Application {
	
	void run();
	Connection getConnection();
	void initConnection() throws SQLException;
	
}
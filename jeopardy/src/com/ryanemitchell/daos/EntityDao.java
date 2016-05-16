package com.ryanemitchell.daos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ryanemitchell.exception.ServerException;

public class EntityDao {
	private Connection conn = null;
	private static EntityDao entityDao = null;
	
	/**
	 * Use the singleton pattern to ensure that only one database connection is open
	 * @throws ServerException 
	 */
	private EntityDao() throws ServerException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new ServerException(e);
		}
		
		try {
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/jeopardy", "root", null);
		} catch (SQLException e) {
		    throw new ServerException(e);
		}
	}
	
	
	public static EntityDao getInstance() throws ServerException {
		if(entityDao == null) {
			entityDao = new EntityDao();
		}
		return entityDao;
	}
	
	/**
	 * Returns a PreparedStatement from the connection. Add variables to the PreparedStatement
	 * @param query
	 * @return
	 * @throws ServerException
	 */
	protected PreparedStatement getPreparedStatement(String query) throws ServerException {
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(query);	
		} catch (SQLException e) {
			throw new ServerException(e);
		}
		return statement;
	}
	
	/**
	 * Used to get a ResultSet from a String. Please do not use this with strings containing
	 * variables. Build the prepared statement first, using getPreparedStatement
	 * @param query
	 * @return
	 * @throws ServerException
	 */
	protected ResultSet get(String query) throws ServerException {
		PreparedStatement statement = getPreparedStatement(query);
		return get(statement);
	}
	
	/**
	 * Get a ResultSet from a PreparedStatement
	 * @param query
	 * @return
	 * @throws ServerException
	 */
	protected ResultSet get(PreparedStatement query) throws ServerException {
		ResultSet result = null;
		try {
			result = query.executeQuery();
		} catch (SQLException e) {
			throw new ServerException(e);
		}
		return result;
	}
	
}

package org.ashish.acoolgames.common.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

public class DBConnectionPool {
	// JDBC Driver Name & Database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String JDBC_DB_URL = "jdbc:mysql://localhost:3306/";

	// JDBC Database Credentials
	static final String JDBC_USER = "root";
	static final String JDBC_PASS = "root";

	private static GenericObjectPool gPool = null;
	private static DBConnectionPool instance;

	private DBConnectionPool() {
		try {
			setUpPool();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	@SuppressWarnings("unused")
	private DataSource setUpPool() throws Exception {
		Class.forName(JDBC_DRIVER);

		// Creates an Instance of GenericObjectPool That Holds Our Pool of Connections Object!
		gPool = new GenericObjectPool();
		gPool.setMaxActive(5);

		// Creates a ConnectionFactory Object Which Will Be Use by the Pool to Create the Connection Object!
		ConnectionFactory cf = new DriverManagerConnectionFactory(JDBC_DB_URL, JDBC_USER, JDBC_PASS);

		// Creates a PoolableConnectionFactory That Will Wraps the Connection Object Created by the ConnectionFactory to Add Object Pooling Functionality!
		PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, gPool, null, null, false, true);
		return new PoolingDataSource(gPool);
	}

	public static DBConnectionPool getInstance()
	{
		if(instance==null)
		{
			synchronized (DBConnectionPool.class) {
				if(instance==null)
				{
					instance = new DBConnectionPool();
				}
			}
		}
		return instance;
	}
	
	public final GenericObjectPool getConnectionPool() {
		return gPool;
	}
	
	public final DataSource getDataSource() {
		printDbStatus();
		return new PoolingDataSource(gPool);
	}
	
	// This Method Is Used To Print The Connection Pool Status
	private final void printDbStatus() {
		System.out.println("Max.: " + getConnectionPool().getMaxActive() + "; Active: " + getConnectionPool().getNumActive() + "; Idle: " + getConnectionPool().getNumIdle());
	}

	public static void main(String[] args) {
		ResultSet rsObj = null;
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		DBConnectionPool jdbcObj = new DBConnectionPool();
		try {	
			DataSource dataSource = jdbcObj.setUpPool();
			jdbcObj.printDbStatus();

			// Performing Database Operation!
			System.out.println("\n=====Making A New Connection Object For Db Transaction=====\n");
			connObj = dataSource.getConnection();
			jdbcObj.printDbStatus(); 

			pstmtObj = connObj.prepareStatement("SELECT * FROM myapartment.UNIT");
			rsObj = pstmtObj.executeQuery();
			while (rsObj.next()) {
				System.out.println("UNIT_NAME: " + rsObj.getString("NAME"));
			}
			System.out.println("\n=====Releasing Connection Object To Pool=====\n");			
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				// Closing ResultSet Object
				if(rsObj != null) {
					rsObj.close();
				}
				// Closing PreparedStatement Object
				if(pstmtObj != null) {
					pstmtObj.close();
				}
				// Closing Connection Object
				if(connObj != null) {
					connObj.close();
				}
			} catch(Exception sqlException) {
				sqlException.printStackTrace();
			}
		}
		jdbcObj.printDbStatus();
	}
}

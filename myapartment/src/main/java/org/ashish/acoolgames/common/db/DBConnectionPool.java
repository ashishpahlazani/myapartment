package org.ashish.acoolgames.common.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;
import org.ashish.acoolgames.common.util.CommonUtil;
import org.ashish.acoolgames.myapartment.exception.ConfigurationException;

public class DBConnectionPool {
	private static final Logger logger = Logger.getLogger(DBConnectionPool.class);

	private static GenericObjectPool gPool = null;
	private static DBConnectionPool instance;

	private DBConnectionPool() {
		try {
			//Properties dbProperties = loadPropertiesFile("freemysqlhosting.properties");
			Properties dbProperties = loadPropertiesFile("localDataSource.properties");
			setUpPool(dbProperties);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	@SuppressWarnings("unused")
	private DataSource setUpPool(Properties dbProperties) throws Exception {
		Class.forName(dbProperties.getProperty("JDBC_DRIVER"));

		// Creates an Instance of GenericObjectPool That Holds Our Pool of Connections Object!
		gPool = new GenericObjectPool();
		gPool.setMaxActive(5);

		String JDBC_DB_URL = dbProperties.getProperty("JDBC_DB_URL") + dbProperties.getProperty("SCHEMA");
		
		logger.debug("JDBC_DB_URL: " + JDBC_DB_URL );
		logger.debug("JDBC_USER: " + dbProperties.getProperty("JDBC_USER"));
		logger.debug("JDBC_PASS: " + dbProperties.getProperty("JDBC_PASS"));
		
		// Creates a ConnectionFactory Object Which Will Be Use by the Pool to Create the Connection Object!
		ConnectionFactory cf = new DriverManagerConnectionFactory(JDBC_DB_URL, 
				dbProperties.getProperty("JDBC_USER"), 
				dbProperties.getProperty("JDBC_PASS"));

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

	public Properties loadPropertiesFile(String fileName)
	{
		Properties dbProperties = new Properties();
		URL url = getClass().getClassLoader().getResource(fileName);
		System.out.println(url.getPath());
		try(FileInputStream fileInputStream = new FileInputStream(url.getFile())) 
		{
			dbProperties.load(fileInputStream);
		} catch (IOException ex) 
		{
			logger.error("Exception while loading properties : " + ex);
			throw new ConfigurationException(ex);
		}
		return dbProperties;
	}
	
	public static void main(String[] args) {
		ResultSet rsObj = null;
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		DBConnectionPool jdbcObj = new DBConnectionPool();
		try {	
			DataSource dataSource = jdbcObj.setUpPool(CommonUtil.loadPropertiesFile("localDataSource.properties"));
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
			logger.error("Exception :" + sqlException);
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
				logger.error("Exception while closing connection" + sqlException);
			}
		}
		jdbcObj.printDbStatus();
	}
}

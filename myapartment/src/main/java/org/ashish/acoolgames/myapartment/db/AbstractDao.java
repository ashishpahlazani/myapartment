package org.ashish.acoolgames.myapartment.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.ashish.acoolgames.myapartment.exception.DataNotFoundException;

public abstract class AbstractDao {
	
	private static final Logger logger = Logger.getLogger(ApartmentDao.class);

	protected static final String EXCEPTION_CLOSING_CONNECTION_LOG = "Exception while closing connection";

	private static final String SELECT_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
	
	protected Long getLastInsertId(Connection connObj)
	{
		ResultSet rsObj = null;
		Statement stmt = null;
		Long id = 0l ;
		try {
			stmt = connObj.createStatement();
			System.out.println("Executing: " + stmt.toString());
			rsObj = stmt.executeQuery(SELECT_LAST_INSERT_ID);
			if(rsObj.next())
			{
				id = rsObj.getLong(1);
			}
			else{
				throw new DataNotFoundException("Id Not found after insert!", new Exception());
			}
		} catch(Exception sqlException) {
			logger.error("Exception getting last insert id: " + sqlException);
			throw new DataNotFoundException("Id Not found after insert!", new Exception());
		} finally {
			try {
				// Closing ResultSet Object
				if(rsObj != null) {
					rsObj.close();
				}
				// Closing PreparedStatement Object
				if(stmt != null) {
					stmt.close();
				}
				// Closing Connection Object
				if(connObj != null) {
					connObj.close();
				}
			} catch(Exception sqlException) {
				logger.error("Exception while closing connection" + sqlException);
			}
		}
		return id;
	}
}

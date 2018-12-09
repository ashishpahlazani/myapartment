package org.ashish.acoolgames.myapartment.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.ashish.acoolgames.myapartment.exception.DataNotFoundException;

public abstract class AbstarctDao {
	
	private static final String SELECT_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
	
	protected int getLastInsertId(Connection connObj)
	{
		ResultSet rsObj = null;
		Statement stmt = null;
		int id = 0 ;
		try {
			stmt = connObj.createStatement();
			System.out.println("Executing: " + stmt.toString());
			rsObj = stmt.executeQuery(SELECT_LAST_INSERT_ID);
			if(rsObj.next())
			{
				id = rsObj.getInt(1);
			}
			else{
				throw new DataNotFoundException("Id Not found after insert!");
			}
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
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
				sqlException.printStackTrace();
			}
		}
		return id;
	}
}

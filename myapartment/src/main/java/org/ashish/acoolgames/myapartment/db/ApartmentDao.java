package org.ashish.acoolgames.myapartment.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.ashish.acoolgames.common.db.DBConnectionPool;
import org.ashish.acoolgames.myapartment.model.Apartment;

public class ApartmentDao extends AbstarctDao{
	private static final Logger logger = Logger.getLogger(ApartmentDao.class);
	
	public String INSERT_APARTMENT_SQL = "INSERT INTO myapartment.apartment ( NAME, "
			+ "DESCRIPTION, ADDRESS, OWNER_NAME, OWNER_CONTACT, ADMIN_NAME, ADMIN_CONTACT) "
			+ "VALUES ( ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String GET_APARTMENT_BY_ID_SQL = "SELECT * FROM myapartment.APARTMENT WHERE APARTMENT_ID=?";
	
	private static final String DELETE_APARTMENT_BY_ID_SQL = "DELETE FROM myapartment.APARTMENT WHERE APARTMENT_ID=?";
	
	public Apartment insertApartment(Apartment apartment)
	{
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		
		try {	
			DataSource dataSource = DBConnectionPool.getInstance().getDataSource();
			connObj = dataSource.getConnection();

			int colNo = 0;
			pstmtObj = connObj.prepareStatement(INSERT_APARTMENT_SQL);
			pstmtObj.setString(++colNo, apartment.getName());
			pstmtObj.setString(++colNo, apartment.getDescription());
			pstmtObj.setString(++colNo, apartment.getAddress());
			pstmtObj.setString(++colNo, apartment.getOwnerName());
			pstmtObj.setString(++colNo, apartment.getOwnerContact());
			pstmtObj.setString(++colNo, apartment.getAdminName());
			pstmtObj.setString(++colNo, apartment.getAdminContact());
			logger.debug("Executing: " + pstmtObj.toString());
			pstmtObj.execute();
			apartment.setApartmentId(getLastInsertId(connObj));
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
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
		return apartment;
	}
	
	public Apartment getApartmentById(Integer apartmentId)
	{
		ResultSet rsObj = null;
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		Apartment apartment = null;
		try {	
			DataSource dataSource = DBConnectionPool.getInstance().getDataSource();
			connObj = dataSource.getConnection();

			int colNo = 0;
			pstmtObj = connObj.prepareStatement(GET_APARTMENT_BY_ID_SQL);
			pstmtObj.setInt(++colNo, apartmentId);
			logger.debug("Executing: " + pstmtObj.toString());
			rsObj = pstmtObj.executeQuery();
			if (rsObj.next()) {
				apartment = getApartmentObjectFromRs(rsObj);
				logger.debug("UNIT : " + apartment);
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
		return apartment;
	}
	
	public Apartment deleteApartment(Integer apartmentId)
	{
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		Apartment apartment = getApartmentById(apartmentId);
		try {	
			DataSource dataSource = DBConnectionPool.getInstance().getDataSource();
			connObj = dataSource.getConnection();
			int colNo = 0;
			pstmtObj = connObj.prepareStatement(DELETE_APARTMENT_BY_ID_SQL);
			pstmtObj.setInt(++colNo, apartmentId);
			logger.debug("Executing: " + pstmtObj.toString());
			int rowCount = pstmtObj.executeUpdate();
			logger.debug("Deleted " + rowCount + " records");
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
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
		return apartment;
	}
	
	//DESCRIPTION, ADDRESS, OWNER_NAME, OWNER_CONTACT, ADMIN_NAME, ADMIN_CONTACT
	private Apartment getApartmentObjectFromRs(ResultSet rsObj) throws SQLException {
		return new Apartment(
				rsObj.getInt("APARTMENT_ID"), 
				rsObj.getString("NAME"), 
				rsObj.getString("DESCRIPTION"), 
				rsObj.getString("ADDRESS"), 
				rsObj.getString("OWNER_NAME"),
				rsObj.getString("OWNER_CONTACT"), 
				rsObj.getString("ADMIN_NAME"), 
				rsObj.getString("ADMIN_CONTACT"),
				null, //mapcoordinates
				rsObj.getTimestamp("CREATIONTS"),
				rsObj.getTimestamp("CREATIONTS"));
	}
}
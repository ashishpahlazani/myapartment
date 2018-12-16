package org.ashish.acoolgames.myapartment.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.ashish.acoolgames.common.db.DBConnectionPool;
import org.ashish.acoolgames.myapartment.exception.AddDataException;
import org.ashish.acoolgames.myapartment.exception.DataNotFoundException;
import org.ashish.acoolgames.myapartment.model.Apartment;

public class ApartmentDao extends AbstractDao{
	private static final Logger logger = Logger.getLogger(ApartmentDao.class);
	
	public String INSERT_APARTMENT_SQL = "INSERT INTO APARTMENT ( NAME, "
			+ "DESCRIPTION, ADDRESS, OWNER_NAME, OWNER_CONTACT, ADMIN_NAME, ADMIN_CONTACT) "
			+ "VALUES ( ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String GET_ALL_APARTMENT_SQL = "SELECT * FROM APARTMENT";
	
	private static final String GET_APARTMENT_BY_ID_SQL = "SELECT * FROM APARTMENT WHERE APARTMENT_ID=?";
	
	private static final String DELETE_APARTMENT_BY_ID_SQL = "DELETE FROM APARTMENT WHERE APARTMENT_ID=?";
	
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
		} catch(SQLException sqlException) {
			logger.error("Exception while adding Apartment: " + sqlException);
			throw new AddDataException("Exception while adding ApartmentId: " , sqlException);
		} finally {
			try {
				// Closing PreparedStatement Object
				if(pstmtObj != null) {
					pstmtObj.close();
				}
				// Closing Connection Object
				if(connObj != null) {
					connObj.close();
					connObj.close();
				}
			} catch(SQLException sqlException) {
				logger.error("Exception while closing connection" + sqlException);
			}
		}
		
		return apartment;
	}
	
	public Apartment getApartmentById(Long apartmentId)
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
			pstmtObj.setLong(++colNo, apartmentId);
			logger.debug("Executing: " + pstmtObj.toString());
			rsObj = pstmtObj.executeQuery();
			if (rsObj.next()) {
				apartment = getApartmentObjectFromRs(rsObj);
				logger.debug("UNIT : " + apartment);
			}
		} catch(SQLException sqlException) {
			logger.error("Exception getting Apartment" + sqlException);
			throw new DataNotFoundException("Exception getting ApartmentId: " + apartmentId , sqlException);
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
			} catch(SQLException sqlException) {
				logger.error("Exception while closing connection" + sqlException);
			}
		}
		return apartment;
	}
	
	public List<Apartment> getAllApartments() {
		ResultSet rsObj = null;
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		List<Apartment> apartmentList = new ArrayList<Apartment>();
		Apartment apartment = null;
		try {	
			DataSource dataSource = DBConnectionPool.getInstance().getDataSource();
			connObj = dataSource.getConnection();
			pstmtObj = connObj.prepareStatement(GET_ALL_APARTMENT_SQL);
			logger.debug("Executing: " + pstmtObj.toString());
			rsObj = pstmtObj.executeQuery();
			while (rsObj.next()) {
				apartment = getApartmentObjectFromRs(rsObj);
				apartmentList.add(apartment);
				logger.debug("UNIT : " + apartment);
			}
		} catch(SQLException sqlException) {
			logger.error("Exception getting Apartments " + sqlException);
			throw new DataNotFoundException("Exception getting Apartments " , sqlException);
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
			} catch(SQLException sqlException) {
				logger.error("Exception while closing connection" + sqlException);
			}
		}
		return apartmentList;
	}
	
	public Apartment deleteApartment(Long apartmentId)
	{
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		Apartment apartment = getApartmentById(apartmentId);
		if(apartment==null)
		{
			Exception e = new RuntimeException("Apartment not found for Id: " + apartmentId);
			throw new DataNotFoundException(e.getMessage(), e);
		}
		try {	
			DataSource dataSource = DBConnectionPool.getInstance().getDataSource();
			connObj = dataSource.getConnection();
			int colNo = 0;
			pstmtObj = connObj.prepareStatement(DELETE_APARTMENT_BY_ID_SQL);
			pstmtObj.setLong(++colNo, apartmentId);
			logger.debug("Executing: " + pstmtObj.toString());
			int rowCount = pstmtObj.executeUpdate();
			logger.debug("Deleted " + rowCount + " records");
		} catch(SQLException sqlException) {
			logger.error("Exception in deleting apartment" + sqlException);
			throw new DataNotFoundException("Exception in deleting ApartmentId: " + apartmentId , sqlException);
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
			} catch(SQLException sqlException) {
				logger.error("Exception while closing connection" + sqlException);
			}
		}
		return apartment;
	}
	
	private Apartment getApartmentObjectFromRs(ResultSet rsObj) throws SQLException {
		return new Apartment(
				rsObj.getLong("APARTMENT_ID"), 
				rsObj.getString("NAME"), 
				rsObj.getString("DESCRIPTION"), 
				rsObj.getString("ADDRESS"), 
				rsObj.getString("OWNER_NAME"),
				rsObj.getString("OWNER_CONTACT"), 
				rsObj.getString("ADMIN_NAME"), 
				rsObj.getString("ADMIN_CONTACT"),
				null, //mapcoordinates
				rsObj.getTimestamp("CREATIONTS"),
				rsObj.getTimestamp("MODIFIYTS"));
	}


}
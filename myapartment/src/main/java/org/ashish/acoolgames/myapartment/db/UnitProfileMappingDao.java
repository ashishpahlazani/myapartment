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
import org.ashish.acoolgames.myapartment.model.Profile;
import org.ashish.acoolgames.myapartment.model.UnitProfileMapping;
import org.ashish.acoolgames.myapartment.types.VisitorMappingType;

public class UnitProfileMappingDao extends AbstractDao{

	private static final Logger logger = Logger.getLogger(UnitProfileMappingDao.class);
	
	private static final String UNIT_PROFILE_MAPPING_COLUMNS = " PM.MAPPING_ID, PM.PROFILE_ID, PM.UNIT_ID, "
			+ "PM.MAPPING_TYPE_ID, PM.DESCRIPTION, PM.CREATIONTS AS MCREATIONTS, PM.MODIFIYTS AS MMODIFIYTS ";
	
	private static final String PROFILE_COLUMNS = " P.NAME, P.CONTACT, P.ADDRESS, P.ALT_CONTACT, "
			+ "P.NATIVE_CITY, P.SKILLS, P.DESCRIPTION, P.PHOTO_ID, P.CREATIONTS, P.MODIFIYTS ";
	
	private static final String INSERT_UNIT_PROFILE_MAPPING_SQL = "INSERT INTO UNIT_PROFILE_MAPPING (PROFILE_ID, "
			+ "UNIT_ID, MAPPING_TYPE_ID,  DESCRIPTION ) VALUES ( ?, ?, ?, ? )";
	
	private static final String GET_PM_BY_ID_SQL = "SELECT "
			+ UNIT_PROFILE_MAPPING_COLUMNS
			+ "FROM UNIT_PROFILE_MAPPING PM "
			+ "WHERE MAPPING_ID=?";
	private static final String GET_ALL_PM_FOR_UNIT_SQL = "SELECT "
			+ UNIT_PROFILE_MAPPING_COLUMNS
			+ "FROM UNIT_PROFILE_MAPPING PM "
			+ "WHERE UNIT_ID=?";
	private static final String GET_PM_FOR_UNIT_AND_MAPPING_TYPE_SQL = "SELECT "
			+ UNIT_PROFILE_MAPPING_COLUMNS
			+ "FROM UNIT_PROFILE_MAPPING PM "
			+ "WHERE UNIT_ID=? AND MAPPING_TYPE_ID=?";
	private static final String GET_ALL_PM_WITH_PROFILE_FOR_UNIT_SQL = "SELECT "
			+ UNIT_PROFILE_MAPPING_COLUMNS + ", "+ PROFILE_COLUMNS 
			+ "FROM UNIT_PROFILE_MAPPING PM JOIN PROFILE P ON PM.PROFILE_ID=P.PROFILE_ID "
			+ "WHERE PM.UNIT_ID=?";
	private static final String GET_PM_WITH_PROFILE_FOR_UNIT_AND_MAPPING_TYPE_SQL = "SELECT "
			+ UNIT_PROFILE_MAPPING_COLUMNS + ", "+ PROFILE_COLUMNS 
			+ "FROM UNIT_PROFILE_MAPPING PM JOIN PROFILE P ON PM.PROFILE_ID=P.PROFILE_ID "
			+ "WHERE PM.UNIT_ID=? AND PM.MAPPING_TYPE_ID=?";
	private static final String DELETE_PROFILE_BY_ID = "DELETE FROM UNIT_PROFILE_MAPPING WHERE MAPPING_ID=?";

	public UnitProfileMapping getMappingById(Long mappingId, boolean populateProfile) 
	{
		ResultSet rsObj = null;
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		UnitProfileMapping mapping = null;
		try {	
			DataSource dataSource = DBConnectionPool.getInstance().getDataSource();
			connObj = dataSource.getConnection();

			int colNo = 0;
			pstmtObj = connObj.prepareStatement(GET_PM_BY_ID_SQL);
			pstmtObj.setLong(++colNo, mappingId);
			logger.debug("Executing: " + pstmtObj.toString());
			rsObj = pstmtObj.executeQuery();
			if (rsObj.next()) {
				mapping = getUnitProfileMappingFromRs(rsObj, populateProfile);
				logger.debug("UNIT : " + mapping);
			}
		} catch(Exception sqlException) {
			logger.error("Exception getting profile mapping: " + sqlException);
			throw new DataNotFoundException("Exception getting profile mapping for mappingId: " + mappingId , sqlException);
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
				logger.error(EXCEPTION_CLOSING_CONNECTION_LOG + sqlException);
			}
		}
		return mapping;
	}
	public UnitProfileMapping insertMapping(UnitProfileMapping unitProfileMapping)
	{
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		
		try {	
			DataSource dataSource = DBConnectionPool.getInstance().getDataSource();
			connObj = dataSource.getConnection();

			int colNo = 0;
			pstmtObj = connObj.prepareStatement(INSERT_UNIT_PROFILE_MAPPING_SQL);
			pstmtObj.setLong(++colNo, unitProfileMapping.getProfileId());
			pstmtObj.setLong(++colNo, unitProfileMapping.getUnitId());
			pstmtObj.setInt(++colNo, unitProfileMapping.getMappingType().getCode());
			pstmtObj.setString(++colNo, unitProfileMapping.getDescription());
			logger.debug("Executing : " + pstmtObj.toString());
			pstmtObj.execute();
			unitProfileMapping.setMappingId(getLastInsertId(connObj));
		} catch(Exception sqlException) {
			logger.error("Exception inserting profile mapping: " + sqlException);
			throw new AddDataException("Exception while adding profile mapping: " + unitProfileMapping , sqlException);
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
				logger.error("Exception while closing connection" + sqlException);
			}
		}
		return unitProfileMapping;
	}

	public List<UnitProfileMapping> getAllMappingsOfUnit(Long unitId, boolean populateProfile)
	{
		ResultSet rsObj = null;
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		List<UnitProfileMapping> mappingList = new ArrayList<UnitProfileMapping>();
		UnitProfileMapping mapping = null;
		try {	
			DataSource dataSource = DBConnectionPool.getInstance().getDataSource();
			connObj = dataSource.getConnection();

			int colNo = 0;
			if(populateProfile)
			{
				pstmtObj = connObj.prepareStatement(GET_ALL_PM_WITH_PROFILE_FOR_UNIT_SQL);
			}
			else
			{
				pstmtObj = connObj.prepareStatement(GET_ALL_PM_FOR_UNIT_SQL);
			}
				
			pstmtObj.setLong(++colNo, unitId);
			logger.debug("Executing: " + pstmtObj.toString());
			rsObj = pstmtObj.executeQuery();
			while (rsObj.next()) {
				mapping = getUnitProfileMappingFromRs(rsObj, populateProfile);
				mappingList.add(mapping);
				logger.debug("UNIT : " + mapping);
			}
		} catch(Exception sqlException) {
			logger.error("Exception getting profile mapping: " + sqlException);
			throw new DataNotFoundException("Exception getting profile mapping for unitId: " + unitId , sqlException);
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
				logger.error(EXCEPTION_CLOSING_CONNECTION_LOG + sqlException);
			}
		}
		return mappingList;
	}

	public List<UnitProfileMapping> getMappingsOfUnit(Long unitId, VisitorMappingType mappingType, boolean populateProfile)
	{
		ResultSet rsObj = null;
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		List<UnitProfileMapping> mappingList = new ArrayList<UnitProfileMapping>();
		UnitProfileMapping mapping = null;
		try {	
			DataSource dataSource = DBConnectionPool.getInstance().getDataSource();
			connObj = dataSource.getConnection();

			int colNo = 0;
			if(populateProfile)
			{
				pstmtObj = connObj.prepareStatement(GET_PM_WITH_PROFILE_FOR_UNIT_AND_MAPPING_TYPE_SQL);
			}
			else
			{
				pstmtObj = connObj.prepareStatement(GET_PM_FOR_UNIT_AND_MAPPING_TYPE_SQL);
			}
			pstmtObj.setLong(++colNo, unitId);
			pstmtObj.setLong(++colNo, mappingType.getCode());
			logger.debug("Executing: " + pstmtObj.toString());
			rsObj = pstmtObj.executeQuery();
			while (rsObj.next()) {
				mapping = getUnitProfileMappingFromRs(rsObj, populateProfile);
				mappingList.add(mapping);
				logger.debug("UNIT : " + mapping);
			}
		} catch(Exception sqlException) {
			logger.error("Exception getting profile mapping : " + sqlException);
			throw new DataNotFoundException("Exception getting mappings for unitId: " + unitId + ", mappingType: " + mappingType , sqlException);
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
				logger.error(EXCEPTION_CLOSING_CONNECTION_LOG + sqlException);
			}
		}
		return mappingList;
	}

	public UnitProfileMapping deleteMapping(Long mappingId,
			boolean populateProfile) {
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		UnitProfileMapping mapping = getMappingById(mappingId, populateProfile);
		if(mapping==null)
		{
			Exception e = new RuntimeException("Could not delete, profile mapping not found for Id: " + mappingId);
			throw new DataNotFoundException(e.getMessage(), e);
		}
		try {	
			DataSource dataSource = DBConnectionPool.getInstance().getDataSource();
			connObj = dataSource.getConnection();
			int colNo = 0;
			pstmtObj = connObj.prepareStatement(DELETE_PROFILE_BY_ID);
			pstmtObj.setLong(++colNo, mappingId);
			logger.debug("Executing: " + pstmtObj.toString());
			int rowCount = pstmtObj.executeUpdate();
			logger.debug("Deleted : " + rowCount + " records");
		} catch(Exception sqlException) {
			logger.error("Exception deleting profile mapping: " + sqlException);
			throw new DataNotFoundException("Exception deleting profile mapping for mappingId: " + mappingId , sqlException);
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
				logger.error(EXCEPTION_CLOSING_CONNECTION_LOG + sqlException);
			}
		}
		return mapping;
	}
	
	private UnitProfileMapping getUnitProfileMappingFromRs(ResultSet rsObj, boolean populateProfile) throws SQLException {
		UnitProfileMapping mapping = new UnitProfileMapping(rsObj.getLong("MAPPING_ID"), 
				rsObj.getLong("UNIT_ID"), 
				rsObj.getLong("PROFILE_ID"), 
				VisitorMappingType.convert( rsObj.getInt("MAPPING_TYPE_ID") ), 
				rsObj.getString("DESCRIPTION"), 
				rsObj.getTimestamp("MCREATIONTS"),
				rsObj.getTimestamp("MMODIFIYTS"));
		if(populateProfile)
		{
			Profile profile = ProfileDao.getProfileObjectFromRs(rsObj);
			mapping.setProfile(profile);
		}
		return mapping;
	}
}

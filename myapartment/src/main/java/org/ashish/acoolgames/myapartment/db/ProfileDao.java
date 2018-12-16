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
import org.ashish.acoolgames.myapartment.model.Profile;

public class ProfileDao extends AbstractDao {

	private static final Logger logger = Logger.getLogger(ProfileDao.class);
	
	private static final String INSERT_PROFILE_SQL = "INSERT INTO PROFILE ( NAME, CONTACT, "
			+ "ADDRESS, ALT_CONTACT, NATIVE_CITY, SKILLS, DESCRIPTION) VALUES (?, ?, ?, ?, ?, ?, ?)";
	
	private static final String DELETE_PROFILE_BY_ID_SQL = "DELETE FROM PROFILE WHERE PROFILE_ID=?";
	
	private static final String GET_PROFILE_BY_ID_SQL = "SELECT PROFILE_ID, NAME, CONTACT, ADDRESS, ALT_CONTACT, "
			+ "NATIVE_CITY, SKILLS, DESCRIPTION, PHOTO_ID, CREATIONTS, MODIFIYTS  FROM PROFILE WHERE PROFILE_ID=?";
	
	private static final String GET_PROFILES_BY_UNITID_SQL = "SELECT P.PROFILE_ID, P.NAME, P.CONTACT, P.ADDRESS, "
			+ "P.ALT_CONTACT, P.NATIVE_CITY, P.SKILLS, P.DESCRIPTION, P.PHOTO_ID, P.CREATIONTS, P.MODIFIYTS "
			+ "FROM PROFILE P JOIN UNIT_PROFILE_MAPPING UPM ON P.PROFILE_ID=UPM.PROFILE_ID WHERE UPM.UNIT_ID=?";
	
	public Profile addProfile(Profile profile)
	{
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		
		try {	
			DataSource dataSource = DBConnectionPool.getInstance().getDataSource();
			connObj = dataSource.getConnection();

			int colNo = 0;
			pstmtObj = connObj.prepareStatement(INSERT_PROFILE_SQL);
			pstmtObj.setString(++colNo, profile.getName());
			pstmtObj.setString(++colNo, profile.getContact());
			pstmtObj.setString(++colNo, profile.getAddress());
			pstmtObj.setString(++colNo, profile.getAltContact());
			pstmtObj.setString(++colNo, profile.getNativeCity());
			pstmtObj.setString(++colNo, profile.getSkills());
			pstmtObj.setString(++colNo, profile.getDescription());
			logger.debug("Executing : " + pstmtObj.toString());
			pstmtObj.execute();
			profile.setProfileId(getLastInsertId(connObj));
		} catch(Exception sqlException) {
			logger.error("Exception inserting profile: " + sqlException);
			throw new AddDataException("Exception while adding Profile: " , sqlException);
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
		return profile;
	}

	public Profile getProfileById(Long profileId)
	{
		ResultSet rsObj = null;
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		Profile profile = null;
		try {	
			DataSource dataSource = DBConnectionPool.getInstance().getDataSource();
			connObj = dataSource.getConnection();

			int colNo = 0;
			pstmtObj = connObj.prepareStatement(GET_PROFILE_BY_ID_SQL);
			pstmtObj.setLong(++colNo, profileId);
			logger.debug("Executing: " + pstmtObj.toString());
			rsObj = pstmtObj.executeQuery();
			if (rsObj.next()) {
				profile = getProfileObjectFromRs(rsObj);
				logger.debug("Profile : " + profile);
			}
		} catch(Exception sqlException) {
			logger.error("Exception getting profile: " + sqlException);
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
		return profile;
	}
	
	public Profile deleteProfile(Long profileId)
	{
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		Profile profile = getProfileById(profileId);
		try {	
			DataSource dataSource = DBConnectionPool.getInstance().getDataSource();
			connObj = dataSource.getConnection();
			int colNo = 0;
			pstmtObj = connObj.prepareStatement(DELETE_PROFILE_BY_ID_SQL);
			pstmtObj.setLong(++colNo, profileId);
			logger.debug("Executing: " + pstmtObj.toString());
			int rowCount = pstmtObj.executeUpdate();
			logger.debug("Deleted : " + rowCount + " records");
		} catch(Exception sqlException) {
			logger.error("Exception deleting profile: " + sqlException);
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
		return profile;
	}
	
	public List<Profile> getAllProfilesOfUnit(Long unitId)
	{
		ResultSet rsObj = null;
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		List<Profile> profileList = new ArrayList<Profile>();
		Profile profile = null;
		try {	
			DataSource dataSource = DBConnectionPool.getInstance().getDataSource();
			connObj = dataSource.getConnection();

			int colNo = 0;
			pstmtObj = connObj.prepareStatement(GET_PROFILES_BY_UNITID_SQL);
			pstmtObj.setLong(++colNo, unitId);
			rsObj = pstmtObj.executeQuery();
			logger.debug("Executing: " + pstmtObj.toString());
			while (rsObj.next()) {
				profile = getProfileObjectFromRs(rsObj);
				profileList.add(profile);
				logger.debug("Profile : " + profile);
			}
		} catch(Exception sqlException) {
			logger.error("Exception getting profile: " + sqlException);
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
				logger.error("Exception while closing connection: " + sqlException);
			}
		}
		return profileList;
	}
	
	private Profile getProfileObjectFromRs(ResultSet rsObj) throws SQLException {
		return new Profile(
				rsObj.getLong("PROFILE_ID"), 
				rsObj.getString("NAME"), 
				rsObj.getString("CONTACT"), 
				rsObj.getString("ADDRESS"), 
				rsObj.getString("NATIVE_CITY"), 
				rsObj.getString("ALT_CONTACT"),
				rsObj.getString("DESCRIPTION"),
				rsObj.getLong("PHOTO_ID"),
				rsObj.getString("SKILLS"),
				rsObj.getTimestamp("CREATIONTS"),
				rsObj.getTimestamp("MODIFIYTS"));
	}
}

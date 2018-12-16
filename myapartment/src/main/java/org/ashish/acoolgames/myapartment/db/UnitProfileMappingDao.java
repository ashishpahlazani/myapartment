package org.ashish.acoolgames.myapartment.db;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.ashish.acoolgames.common.db.DBConnectionPool;
import org.ashish.acoolgames.myapartment.exception.AddDataException;
import org.ashish.acoolgames.myapartment.model.UnitProfileMapping;

public class UnitProfileMappingDao extends AbstractDao{

	private static final Logger logger = Logger.getLogger(UnitProfileMappingDao.class);
	
	private static final String INSERT_UNIT_PROFILE_MAPPING_SQL = "INSERT INTO UNIT_PROFILE_MAPPING (PROFILE_ID, "
			+ "UNIT_ID, MAPPING_TYPE_ID,  DESCRIPTION ) VALUES ( ?, ?, ?, ? )";
	
	
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
			//unitProfileMapping.setUnitId(getLastInsertId(connObj));
		} catch(Exception sqlException) {
			logger.error("Exception inserting profile mapping: " + sqlException);
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
				}
			} catch(Exception sqlException) {
				logger.error("Exception while closing connection" + sqlException);
			}
		}
		return unitProfileMapping;
	}
	
}

package org.ashish.acoolgames.myapartment.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.ashish.acoolgames.common.db.DBConnectionPool;
import org.ashish.acoolgames.myapartment.model.Unit;

public class UnitDao extends AbstarctDao
{
	private static final Logger logger = Logger.getLogger(ApartmentDao.class);
	
	private static final String INSERT_UNIT_SQL = "INSERT INTO myapartment.unit ( UNIT_TYPE_ID, NAME, PARENT_UNIT_ID, "
			+ "APARTMENT_ID, HIERARCHY, DESCRIPTION) VALUES (?, ?, ?, ?, ?, ?)";
	
	private static final String DELETE_UNIT_BY_ID_SQL = "DELETE FROM myapartment.unit WHERE UNIT_ID=?";
	
	private static final String GET_UNIT_BY_ID_SQL = "SELECT UNIT_ID, UNIT_TYPE_ID, NAME, PARENT_UNIT_ID, APARTMENT_ID, "
			+ "HIERARCHY, DESCRIPTION FROM myapartment.unit WHERE UNIT_ID=?";
	
	private static final String GET_UNITS_BY_APARTMENT_ID_SQL = "SELECT UNIT_ID, UNIT_TYPE_ID, NAME, PARENT_UNIT_ID, "
			+ "APARTMENT_ID, HIERARCHY, DESCRIPTION FROM myapartment.unit WHERE APARTMENT_ID=? "
			+ "ORDER BY HIERARCHY";
	
	private static final String GET_CHILD_UNITS_SQL = "SELECT * FROM myapartment.unit WHERE PARENT_UNIT_ID=?";
	
	public Unit insertUnit(Unit unit)
	{
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		
		try {	
			DataSource dataSource = DBConnectionPool.getInstance().getDataSource();
			connObj = dataSource.getConnection();

			int colNo = 0;
			pstmtObj = connObj.prepareStatement(INSERT_UNIT_SQL);
			pstmtObj.setInt(++colNo, unit.getUnitType().getUnitTypeId());
			pstmtObj.setString(++colNo, unit.getName());
			pstmtObj.setInt(++colNo, unit.getParentUnit());
			pstmtObj.setInt(++colNo, unit.getApartmentId());
			pstmtObj.setInt(++colNo, unit.getHierarchy());
			pstmtObj.setString(++colNo, unit.getDescription());
			logger.debug("Executing : " + pstmtObj.toString());
			pstmtObj.execute();
			unit.setUnitId(getLastInsertId(connObj));
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
		return unit;
	}
	
	public Unit getUnitById(Integer unitId)
	{
		ResultSet rsObj = null;
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		Unit unit = null;
		try {	
			DataSource dataSource = DBConnectionPool.getInstance().getDataSource();
			connObj = dataSource.getConnection();

			int colNo = 0;
			pstmtObj = connObj.prepareStatement(GET_UNIT_BY_ID_SQL);
			pstmtObj.setInt(++colNo, unitId);
			logger.debug("Executing: " + pstmtObj.toString());
			rsObj = pstmtObj.executeQuery();
			if (rsObj.next()) {
				unit = getUnitObjectFromRs(rsObj);
				logger.debug("UNIT : " + unit);
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
		return unit;
	}
	
	public Unit deleteUnit(Integer unitId)
	{
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		Unit unit = getUnitById(unitId);
		try {	
			DataSource dataSource = DBConnectionPool.getInstance().getDataSource();
			connObj = dataSource.getConnection();
			int colNo = 0;
			pstmtObj = connObj.prepareStatement(DELETE_UNIT_BY_ID_SQL);
			pstmtObj.setInt(++colNo, unitId);
			logger.debug("Executing: " + pstmtObj.toString());
			int rowCount = pstmtObj.executeUpdate();
			logger.debug("Deleted : " + rowCount + " records");
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
		return unit;
	}
	
	public List<Unit> getAllUnitsOfApartment(Integer apartmentId)
	{
		ResultSet rsObj = null;
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		List<Unit> unitList = new ArrayList<Unit>();
		Unit unit = null;
		try {	
			DataSource dataSource = DBConnectionPool.getInstance().getDataSource();
			connObj = dataSource.getConnection();

			int colNo = 0;
			pstmtObj = connObj.prepareStatement(GET_UNITS_BY_APARTMENT_ID_SQL);
			pstmtObj.setInt(++colNo, apartmentId);
			rsObj = pstmtObj.executeQuery();
			logger.debug("Executing: " + pstmtObj.toString());
			while (rsObj.next()) {
				unit = getUnitObjectFromRs(rsObj);
				unitList.add(unit);
				logger.debug("UNIT : " + unit);
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
		return unitList;
	}

	public Collection<Unit> getUnitsTreeOfApartment(Integer apartmentId)
	{
		ResultSet rsObj = null;
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		Map<Integer, Unit> unitMap = new HashMap<Integer, Unit>();
		Unit unit = null;
		try {	
			DataSource dataSource = DBConnectionPool.getInstance().getDataSource();
			connObj = dataSource.getConnection();

			int colNo = 0;
			pstmtObj = connObj.prepareStatement(GET_UNITS_BY_APARTMENT_ID_SQL);
			pstmtObj.setInt(++colNo, apartmentId);
			rsObj = pstmtObj.executeQuery();
			logger.debug("Executing: " + pstmtObj.toString());
			while (rsObj.next()) {
				unit = getUnitObjectFromRs(rsObj);
				if( unit.getParentUnit() != null && unit.getParentUnit() != 0 )
				{
					unitMap.get(unit.getParentUnit()).addChild(unit);
				}
				unitMap.put(unit.getUnitId(), unit);
			}
			logger.debug( unitMap );
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
		
		List<Unit> topUnitList = new ArrayList<Unit>();
		for(Unit unit1 : unitMap.values())
		{
			if(unit1.getParentUnit()==null || unit1.getParentUnit()==0)
			{
				topUnitList.add(unit1);
			}
		}
		
		return topUnitList;
	}
	
	public List<Unit> getAllChildUnits(Integer parentUnitId)
	{
		ResultSet rsObj = null;
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		List<Unit> unitList = new ArrayList<Unit>();
		Unit unit = null;
		try {	
			DataSource dataSource = DBConnectionPool.getInstance().getDataSource();
			connObj = dataSource.getConnection();

			int colNo = 0;
			pstmtObj = connObj.prepareStatement(GET_CHILD_UNITS_SQL);
			pstmtObj.setInt(++colNo, parentUnitId);
			rsObj = pstmtObj.executeQuery();
			logger.debug("Executing: " + pstmtObj.toString());
			while (rsObj.next()) {
				unit = getUnitObjectFromRs(rsObj);
				unitList.add(unit);
				logger.debug("UNIT : " + unit);
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
		return unitList;
	}

	private Unit getUnitObjectFromRs(ResultSet rsObj) throws SQLException {
		return new Unit(
				rsObj.getInt("UNIT_ID"), 
				rsObj.getInt("APARTMENT_ID"), 
				rsObj.getString("DESCRIPTION"), 
				rsObj.getInt("HIERARCHY"), 
				rsObj.getString("NAME"), 
				rsObj.getInt("PARENT_UNIT_ID"),
				new UnitTypeDao().getUnitType(rsObj.getInt("UNIT_TYPE_ID")));
	}

}

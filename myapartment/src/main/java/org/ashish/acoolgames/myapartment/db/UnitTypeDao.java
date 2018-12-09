package org.ashish.acoolgames.myapartment.db;

import java.util.HashMap;
import java.util.Map;

import org.ashish.acoolgames.myapartment.model.UnitType;

public class UnitTypeDao {
	private static Map<Integer, UnitType> unitTypeMap = new HashMap<>();
	
	static{
		init();
	}
	
	public static void init()
	{
		populateUnitTypeMap();
	}

	private static void populateUnitTypeMap() {
		unitTypeMap.put(1, new UnitType(1, "BLOCK", "Block", null, null));
		unitTypeMap.put(2, new UnitType(2, "FLAT", "FLAT", null, null));
	}
	
	public UnitType getUnitType(Integer unitTypeId)
	{
		return unitTypeMap.get(unitTypeId);
	}
}

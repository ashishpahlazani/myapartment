package org.ashish.acoolgames.myapartment.service;

import org.ashish.acoolgames.myapartment.db.UnitProfileMappingDao;
import org.ashish.acoolgames.myapartment.model.UnitProfileMapping;

public class UnitProfileMappingService {
	private static UnitProfileMappingDao unitProfileMappingDao = new UnitProfileMappingDao();
	
	public UnitProfileMapping addUnitProfileMapping(UnitProfileMapping mapping) {
		return unitProfileMappingDao.insertMapping(mapping);
	}
}

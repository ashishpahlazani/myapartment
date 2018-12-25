package org.ashish.acoolgames.myapartment.service;

import java.util.List;

import org.ashish.acoolgames.myapartment.db.UnitProfileMappingDao;
import org.ashish.acoolgames.myapartment.model.UnitProfileMapping;
import org.ashish.acoolgames.myapartment.types.VisitorMappingType;

public class UnitProfileMappingService {
	private static UnitProfileMappingDao unitProfileMappingDao = new UnitProfileMappingDao();
	
	public UnitProfileMapping addUnitProfileMapping(UnitProfileMapping mapping) {
		mapping.validate(mapping);
		return unitProfileMappingDao.insertMapping(mapping);
	}
	
	public List<UnitProfileMapping> getAllMappingsOfUnit(Long unitId, boolean populateProfile)
	{
		return unitProfileMappingDao.getAllMappingsOfUnit(unitId, populateProfile);
	}
	
	public List<UnitProfileMapping> getMappingsOfUnit(Long unitId, VisitorMappingType mappingType, boolean populateProfile)
	{
		return unitProfileMappingDao.getMappingsOfUnit(unitId, mappingType, populateProfile);
	}

	public UnitProfileMapping removeMapping(Long mappingId, boolean populateProfile) {
		return unitProfileMappingDao.deleteMapping(mappingId, populateProfile);
	}

	public UnitProfileMapping getMappingById(Long mappingId, boolean populateProfile) {
		return unitProfileMappingDao.getMappingById(mappingId, populateProfile);
	}
}

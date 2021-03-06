package org.ashish.acoolgames.myapartment.service;

import java.util.Collection;
import java.util.List;

import org.ashish.acoolgames.myapartment.db.UnitDao;
import org.ashish.acoolgames.myapartment.model.Unit;

public class UnitService {
	
	private static UnitDao unitDao = new UnitDao();
	
	public List<Unit> getAllUnitsOfApartment(Long apartmentId) 
	{
		return unitDao.getAllUnitsOfApartment(apartmentId);
	}
	
	public Collection<Unit> getUnitsTreeOfApartment(Long apartmentId) 
	{
		return unitDao.getUnitsTreeOfApartment(apartmentId);
	}
	
	public Unit getUnit(Long unitId) 
	{
		return unitDao.getUnitById(unitId);
	}

	public Unit addUnit(Unit unit) 
	{
		unit.validate(unit);
		return unitDao.insertUnit(unit);
	}

	public Unit removeUnit(Long unitId) {
		return unitDao.deleteUnit(unitId);
	}
	
}

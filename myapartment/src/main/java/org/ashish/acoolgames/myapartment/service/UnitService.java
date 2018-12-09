package org.ashish.acoolgames.myapartment.service;

import java.util.Collection;
import java.util.List;

import org.ashish.acoolgames.myapartment.db.UnitDao;
import org.ashish.acoolgames.myapartment.model.Unit;

public class UnitService {
	
	private static UnitDao unitDao = new UnitDao();
	
	public List<Unit> getAllUnitsOfApartment(Integer apartmentId) 
	{
		return unitDao.getAllUnitsOfApartment(apartmentId);
	}
	
	public Collection<Unit> getUnitsTreeOfApartment(Integer apartmentId) 
	{
		return unitDao.getUnitsTreeOfApartment(apartmentId);
	}
	
	public Unit getUnit(Integer unitId) 
	{
		return unitDao.getUnitById(unitId);
	}

	public Unit addUnit(Unit unit) 
	{
		return unitDao.insertUnit(unit);
	}

	public Unit removeUnit(Integer unitId) {
		return unitDao.deleteUnit(unitId);
	}
	
}

package org.ashish.acoolgames.myapartment.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.ashish.acoolgames.myapartment.exception.AddDataException;


/**
 * The persistent class for the unit database table.
 * 
 */
@XmlRootElement
public class Unit implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long unitId;

	private Long apartmentId;

	private Timestamp creationts;

	private String description;

	private int hierarchy;

	private Timestamp modifiyts;

	private String name;

	private Long parentUnitId;

	private UnitType unitType;
	
	private Unit parentUnit;
	
	@XmlElement(name = "childUnits")
	private List<Unit> childUnits;

	public Unit() {
	}

	public Unit(Long unitId, Long apartmentId, String description, 
			int hierarchy, String name, Long parentUnitId, UnitType unitType) {
		super();
		this.unitId = unitId;
		this.apartmentId = apartmentId;
		this.description = description;
		this.hierarchy = hierarchy;
		this.name = name;
		this.parentUnitId = parentUnitId;
		this.unitType = unitType;
	}

	public Long getUnitId() {
		return this.unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public Long getApartmentId() {
		return this.apartmentId;
	}

	public void setApartmentId(Long apartmentId) {
		this.apartmentId = apartmentId;
	}

	public Timestamp getCreationts() {
		return this.creationts;
	}

	public void setCreationts(Timestamp creationts) {
		this.creationts = creationts;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getHierarchy() {
		return this.hierarchy;
	}

	public void setHierarchy(int hierarchy) {
		this.hierarchy = hierarchy;
	}

	public Timestamp getModifiyts() {
		return this.modifiyts;
	}

	public void setModifiyts(Timestamp modifiyts) {
		this.modifiyts = modifiyts;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentUnit() {
		return this.parentUnitId;
	}

	public void setParentUnit(Long parentUnitId) {
		this.parentUnitId = parentUnitId;
	}

	public UnitType getUnitType() {
		return this.unitType;
	}

	public void setUnitType(UnitType unitType) {
		this.unitType = unitType;
	}

	@Override
	public String toString() {
		return "Unit [unitId=" + unitId + ", apartmentId=" + apartmentId
				+ ", creationts=" + creationts + ", description=" + description
				+ ", hierarchy=" + hierarchy + ", modifiyts=" + modifiyts
				+ ", name=" + name + ", parentUnitId=" + parentUnitId
				+ ", unitType=" + unitType + ", parentUnit=" + parentUnit
				+ ", childUnits=" + childUnits + "]";
	}

	public void addChild(Unit unit) {
		if(childUnits==null)
		{
			childUnits = new ArrayList<Unit>();
		}
		childUnits.add(unit);
	}
	
	public void validate(Unit unit) {
		StringBuilder errorMessage = new StringBuilder();
		
		if(unit.getName()==null)
		{
			errorMessage.append("Unit name cannot be null!\n");
		}
		if(unit.getApartmentId()==null)
		{
			errorMessage.append("Apartment Id cannot be null!\n");
		}
		if(unit.getUnitType()==null)
		{
			errorMessage.append("Unit type cannot be null!\n");
		}
		
		if(errorMessage.length()!=0)
		{
			throw new AddDataException(errorMessage.toString(), new RuntimeException(""));
		}
	}
}
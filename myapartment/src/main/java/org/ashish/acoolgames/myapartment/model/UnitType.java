package org.ashish.acoolgames.myapartment.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The persistent class for the unit_type database table.
 * 
 */
@XmlRootElement
public class UnitType implements Serializable {
	private static final long serialVersionUID = 1L;

	private int unitTypeId;
	private String name;
	private String description;
	private Timestamp creationts;
	private Timestamp modifiyts;

	public UnitType() {
	}

	public UnitType(int unitTypeId, String name, String description,
			Timestamp modifiyts, Timestamp creationts) {
		super();
		this.unitTypeId = unitTypeId;
		this.creationts = creationts;
		this.description = description;
		this.modifiyts = modifiyts;
		this.name = name;
	}

	public int getUnitTypeId() {
		return this.unitTypeId;
	}

	public void setUnitTypeId(int unitTypeId) {
		this.unitTypeId = unitTypeId;
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

}
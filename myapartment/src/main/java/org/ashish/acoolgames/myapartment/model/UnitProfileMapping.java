package org.ashish.acoolgames.myapartment.model;

import java.sql.Timestamp;

import org.ashish.acoolgames.myapartment.types.VisitorMappingType;

public class UnitProfileMapping {
	private Long unitId;
	private Long profileId;
	private VisitorMappingType mappingType;
	private String description;
	private Timestamp creationts;
	private Timestamp modifiyts;
	
	public UnitProfileMapping() {
		super();
	}
	public UnitProfileMapping(Long unitId, Long profileId, VisitorMappingType mappingType, 
			String description, Timestamp creationts, Timestamp modifiyts) {
		super();
		this.unitId = unitId;
		this.profileId = profileId;
		this.mappingType = mappingType;
		this.description = description;
		this.creationts = creationts;
		this.modifiyts = modifiyts;
	}
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	public Long getProfileId() {
		return profileId;
	}
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}
	public VisitorMappingType getMappingType() {
		return mappingType;
	}
	public void setMappingType(VisitorMappingType mappingType) {
		this.mappingType = mappingType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getCreationts() {
		return creationts;
	}
	public Timestamp getModifiyts() {
		return modifiyts;
	}
	@Override
	public String toString() {
		return "UnitProfileMapping [unitId=" + unitId + ", profileId="
				+ profileId + ", mappingType=" + mappingType + "]";
	}
	
}

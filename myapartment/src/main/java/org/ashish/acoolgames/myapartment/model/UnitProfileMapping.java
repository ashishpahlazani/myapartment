package org.ashish.acoolgames.myapartment.model;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

import org.ashish.acoolgames.myapartment.exception.AddDataException;
import org.ashish.acoolgames.myapartment.types.VisitorMappingType;

@XmlRootElement
public class UnitProfileMapping {
	private Long mappingId;
	private Long unitId;
	private Long profileId;
	private VisitorMappingType mappingType;
	private String description;
	private Timestamp creationts;
	private Timestamp modifiyts;
	//selective population
	private Profile profile;
	
	public UnitProfileMapping() {
		super();
	}
	public UnitProfileMapping(Long mappingId, Long unitId, Long profileId, VisitorMappingType mappingType, 
			String description, Timestamp creationts, Timestamp modifiyts) {
		super();
		this.mappingId = mappingId;
		this.unitId = unitId;
		this.profileId = profileId;
		this.mappingType = mappingType;
		this.description = description;
		this.creationts = creationts;
		this.modifiyts = modifiyts;
	}
	public Long getMappingId() {
		return mappingId;
	}
	public void setMappingId(Long mappingId) {
		this.mappingId = mappingId;
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
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	@Override
	public String toString() {
		return "UnitProfileMapping [unitId=" + unitId + ", profileId="
				+ profileId + ", mappingType=" + mappingType + "]";
	}
	public void validate(UnitProfileMapping mapping) {
		StringBuilder errorMessage = new StringBuilder();
		
		if(mapping.getMappingType()==null)
		{
			errorMessage.append("Mapping type cannot be null!\n");
		}
		if(mapping.getProfileId()==null)
		{
			errorMessage.append("Profile Id cannot be null!\n");
		}
		if(mapping.getUnitId()==null)
		{
			errorMessage.append("Unit id cannot be null!\n");
		}
		
		if(errorMessage.length()!=0)
		{
			throw new AddDataException(errorMessage.toString(), new RuntimeException(""));
		}
	}
}

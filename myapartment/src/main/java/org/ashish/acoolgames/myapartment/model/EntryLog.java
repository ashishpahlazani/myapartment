package org.ashish.acoolgames.myapartment.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

import org.ashish.acoolgames.myapartment.exception.AddDataException;


/**
 * The persistent class for the entry_log database table.
 * 
 */
@XmlRootElement
public class EntryLog implements Serializable {
	private static final long serialVersionUID = 1L;

	private String entryId;

	private Timestamp creationts;

	private String description;

	private Timestamp ints;

	private Timestamp modifiyts;

	private Timestamp outts;

	private BigInteger profileId;

	private String reason;

	private Integer unitId;

	public EntryLog() {
	}

	public String getEntryId() {
		return this.entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
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

	public Timestamp getInts() {
		return this.ints;
	}

	public void setInts(Timestamp ints) {
		this.ints = ints;
	}

	public Timestamp getModifiyts() {
		return this.modifiyts;
	}

	public void setModifiyts(Timestamp modifiyts) {
		this.modifiyts = modifiyts;
	}

	public Timestamp getOutts() {
		return this.outts;
	}

	public void setOutts(Timestamp outts) {
		this.outts = outts;
	}

	public BigInteger getProfileId() {
		return this.profileId;
	}

	public void setProfileId(BigInteger profileId) {
		this.profileId = profileId;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getUnitId() {
		return this.unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
	
	public static void validate(EntryLog entryLog) {
		StringBuilder errorMessage = new StringBuilder();
		
		if(entryLog.getProfileId()==null)
		{
			errorMessage.append("Profile id cannot be null!\n");
		}
		if(entryLog.getUnitId()==null)
		{
			errorMessage.append("Unit Id cannot be null!\n");
		}
		if(entryLog.getReason()==null)
		{
			errorMessage.append("Reason cannot be null!\n");
		}
		
		if(errorMessage.length()!=0)
		{
			throw new AddDataException(errorMessage.toString(), new RuntimeException(""));
		}
	}
}
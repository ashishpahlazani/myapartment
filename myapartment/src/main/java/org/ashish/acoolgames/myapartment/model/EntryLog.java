package org.ashish.acoolgames.myapartment.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;


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

	private int unitId;

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

	public int getUnitId() {
		return this.unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

}
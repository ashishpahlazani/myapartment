package org.ashish.acoolgames.myapartment.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the apartment database table.
 * 
 */
@XmlRootElement
public class Apartment implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long apartmentId;

	private String address;

	private String adminContact;

	private String adminName;

	private String description;

	private String mapCoordinates;

	private String name;

	private String ownerContact;

	private String ownerName;

	private Timestamp creationts;

	private Timestamp modifiyts;

	public Apartment() {
	}
	
	public Apartment(Long apartmentId, String name, String description, 
			String address, String ownerName, String ownerContact, 
			String adminName, String adminContact, String mapCoordinates, 
			Timestamp creationts, Timestamp modifiyts) {
		super();
		this.apartmentId = apartmentId;
		this.address = address;
		this.adminContact = adminContact;
		this.adminName = adminName;
		this.description = description;
		this.mapCoordinates = mapCoordinates;
		this.name = name;
		this.ownerContact = ownerContact;
		this.ownerName = ownerName;
		this.creationts = creationts;
		this.modifiyts = modifiyts;
	}

	public Long getApartmentId() {
		return this.apartmentId;
	}

	public void setApartmentId(Long apartmentId) {
		this.apartmentId = apartmentId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAdminContact() {
		return this.adminContact;
	}

	public void setAdminContact(String adminContact) {
		this.adminContact = adminContact;
	}

	public String getAdminName() {
		return this.adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
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

	public String getMapCoordinates() {
		return this.mapCoordinates;
	}

	public void setMapCoordinates(String mapCoordinates) {
		this.mapCoordinates = mapCoordinates;
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

	public String getOwnerContact() {
		return this.ownerContact;
	}

	public void setOwnerContact(String ownerContact) {
		this.ownerContact = ownerContact;
	}

	public String getOwnerName() {
		return this.ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

}
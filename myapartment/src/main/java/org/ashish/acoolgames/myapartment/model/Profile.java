package org.ashish.acoolgames.myapartment.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the profile database table.
 * 
 */
@XmlRootElement
public class Profile implements Serializable {
	private static final long serialVersionUID = 1L;

	private String profileId;

	private String address;

	private String altContact;

	private String contact;

	private Timestamp creationts;

	private String description;

	private Timestamp modifiyts;

	private String name;

	private String nativeCity;

	private BigInteger photoId;

	private String skills;

	public Profile() {
	}

	public String getProfileId() {
		return this.profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAltContact() {
		return this.altContact;
	}

	public void setAltContact(String altContact) {
		this.altContact = altContact;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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

	public String getNativeCity() {
		return this.nativeCity;
	}

	public void setNativeCity(String nativeCity) {
		this.nativeCity = nativeCity;
	}

	public BigInteger getPhotoId() {
		return this.photoId;
	}

	public void setPhotoId(BigInteger photoId) {
		this.photoId = photoId;
	}

	public String getSkills() {
		return this.skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

}
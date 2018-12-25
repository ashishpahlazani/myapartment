package org.ashish.acoolgames.myapartment.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

import org.ashish.acoolgames.myapartment.exception.AddDataException;


/**
 * The persistent class for the profile database table.
 * 
 */
@XmlRootElement
public class Profile implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long profileId;

	private String address;

	private String altContact;

	private String contact;

	private Timestamp creationts;

	private String description;

	private Timestamp modifiyts;

	private String name;

	private String nativeCity;

	private Long photoId;

	private String skills;

	public Profile() {
	}
	
	public Profile(Long profileId, String name, String contact, String address, 
			String nativeCity, String altContact, String description,Long photoId, 
			String skills, Timestamp creationts, Timestamp modifiyts) {
		super();
		this.profileId = profileId;
		this.address = address;
		this.altContact = altContact;
		this.contact = contact;
		this.description = description;
		this.name = name;
		this.nativeCity = nativeCity;
		this.photoId = photoId;
		this.skills = skills;
		this.creationts = creationts;
		this.modifiyts = modifiyts;
	}



	public Long getProfileId() {
		return this.profileId;
	}

	public void setProfileId(Long profileId) {
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

	public Long getPhotoId() {
		return this.photoId;
	}

	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

	public String getSkills() {
		return this.skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}
	
	public void validate(Profile profile) {
		StringBuilder errorMessage = new StringBuilder();
		
		if(profile.getName()==null)
		{
			errorMessage.append("Profile name cannot be null!\n");
		}
		if(profile.getContact()==null)
		{
			errorMessage.append("Profile contact cannot be null!\n");
		}
		
		if(errorMessage.length()!=0)
		{
			throw new AddDataException(errorMessage.toString(), new RuntimeException(""));
		}
	}
}
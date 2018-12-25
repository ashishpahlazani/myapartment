package org.ashish.acoolgames.myapartment.service;

import java.util.List;

import org.ashish.acoolgames.myapartment.db.ProfileDao;
import org.ashish.acoolgames.myapartment.model.Profile;

public class ProfileService {
	private static ProfileDao profileDao = new ProfileDao();
	
	public List<Profile> getAllProfilesForUnit(Long unitId) 
	{
		return profileDao.getAllProfilesOfUnit(unitId);
	}
	
	public Profile getProfileById(Long profileId) 
	{
		return profileDao.getProfileById(profileId);
	}

	public Profile addProfile(Profile profile) 
	{
		profile.validate(profile);
		return profileDao.addProfile(profile);
	}

	public Profile removeProfile(Long profileId) 
	{
		return profileDao.deleteProfile(profileId);
	} 
	
}

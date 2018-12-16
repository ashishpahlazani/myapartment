package org.ashish.acoolgames.myapartment.resource;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.ashish.acoolgames.myapartment.exception.DataNotFoundException;
import org.ashish.acoolgames.myapartment.model.Profile;
import org.ashish.acoolgames.myapartment.service.ProfileService;

@Path("/profile")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfileResource 
{
	private static final Logger logger = Logger.getLogger(ProfileResource.class);
	
	ProfileService profileService = new ProfileService();
    
    @POST
    public Response addProfile(@PathParam("apartmentId") Long apartmentId,
    		@PathParam("unitId") Long unitId, Profile profile, @Context UriInfo uriInfo) 
    {
    	Profile newProfile = profileService.addProfile(profile);
    	URI uri = uriInfo.getAbsolutePathBuilder().path(newProfile.getProfileId().toString()).build();
    	return Response.created(uri).entity(newProfile).build();
    }
    
    @GET
    public Response getAllProfilesForUnit(@PathParam("apartmentId") Long apartmentId,
    		@PathParam("unitId") Long unitId) 
	{
    	List<Profile> profileList = profileService.getAllProfilesForUnit(unitId);
    	if(profileList==null || profileList.size()==0)
    	{
    		Exception e = new RuntimeException("no profile found for unitId: " + unitId);
			throw new DataNotFoundException(e.getMessage(), e);
    	}
    	final GenericEntity<List<Profile>> entity = new GenericEntity<List<Profile>>(profileList) {};
    	return Response.ok().entity(entity).build();
    }
    
    @GET
    @Path("/{profileId}")
    public Response getProfile(@PathParam("apartmentId") Long apartmentId,
    		@PathParam("unitId") Long unitId, @PathParam("profileId") Long profileId) 
	{
    	Profile profile = profileService.getProfileById(profileId);
    	if(profile==null)
    	{
    		Exception e = new RuntimeException("Profile not found for Id: " + profileId);
			throw new DataNotFoundException(e.getMessage(), e);
    	}
    	return Response.ok().entity(profile).build();
    }
	
	@DELETE
    @Path("{profileId}")
    public Response removeProfile(@PathParam("apartmentId") Long apartmentId,
    		@PathParam("unitId") Long unitId, @PathParam("profileId") Long profileId) 
	{
		logger.debug("removing profileId " + profileId );
		Profile deletedProfile = profileService.removeProfile(profileId);
    	return Response.ok().entity(deletedProfile).build();
    }
}
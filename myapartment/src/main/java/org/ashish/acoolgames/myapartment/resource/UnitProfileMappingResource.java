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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.ashish.acoolgames.myapartment.exception.DataNotFoundException;
import org.ashish.acoolgames.myapartment.model.UnitProfileMapping;
import org.ashish.acoolgames.myapartment.service.UnitProfileMappingService;
import org.ashish.acoolgames.myapartment.types.VisitorMappingType;

@Path("/profilemapping")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UnitProfileMappingResource 
{
	private static final Logger logger = Logger.getLogger(UnitProfileMappingResource.class);
	
	private static Boolean populateProfile = false;
	private UnitProfileMappingService unitProfileMappingServiceService = new UnitProfileMappingService();
    
    @POST
    public Response addUnitProfileMapping(@PathParam("apartmentId") Long apartmentId,
    		@PathParam("unitId") Long unitId, UnitProfileMapping mapping, @Context UriInfo uriInfo) 
    {
    	UnitProfileMapping newMapping = unitProfileMappingServiceService.addUnitProfileMapping(mapping);
    	URI uri = uriInfo.getAbsolutePathBuilder().path(newMapping.getMappingId().toString()).build();
    	return Response.created(uri).entity(newMapping).build();
    }
	
    @GET
    @Path("/{mappingId}")
    public Response getMappingById(@PathParam("apartmentId") Long apartmentId,
    		@PathParam("mappingId") Long mappingId) 
	{
		UnitProfileMapping mapping = unitProfileMappingServiceService.getMappingById(mappingId, populateProfile);
    	if(mapping==null)
    	{
    		Exception e = new RuntimeException("no mapping found ");
			throw new DataNotFoundException(e.getMessage(), e);
    	}
    	return Response.ok().entity(mapping).build();
    }
    
	@GET
    public Response getMappingsOfUnit(@PathParam("apartmentId") Long apartmentId,
    		@PathParam("unitId") Long unitId, @QueryParam("mappingType") Integer mappingType) 
	{
		List<UnitProfileMapping> mappingList = null;
		
		if(mappingType!=null)
		{
			mappingList = unitProfileMappingServiceService.getMappingsOfUnit(
    			unitId, VisitorMappingType.convert(mappingType), populateProfile);
		}
		else
		{
			mappingList = unitProfileMappingServiceService.getAllMappingsOfUnit(
	    			unitId, populateProfile);
		}
    	if(mappingList==null || mappingList.isEmpty())
    	{
    		Exception e = new RuntimeException("no mapping found ");
			throw new DataNotFoundException(e.getMessage(), e);
    	}
    	final GenericEntity<List<UnitProfileMapping>> entity = new GenericEntity<List<UnitProfileMapping>>(mappingList) {};
    	return Response.ok().entity(entity).build();
    }
	
	@DELETE
    @Path("{profileMapping}")
    public Response deleteUnitProfileMapping(@PathParam("apartmentId") Long apartmentId,
    		@PathParam("unitId") Long unitId, @PathParam("profileMapping") Long mappingId) 
	{
		logger.debug("removing mappingId: " + mappingId);
		UnitProfileMapping deletedUnit = unitProfileMappingServiceService
				.removeMapping(mappingId, false);
		return Response.ok().entity(deletedUnit).build();
    }
}

package org.ashish.acoolgames.myapartment.resource;

import java.net.URI;
import java.util.Collection;
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
import org.ashish.acoolgames.myapartment.model.Unit;
import org.ashish.acoolgames.myapartment.service.UnitService;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UnitResource 
{
	private static final Logger logger = Logger.getLogger(UnitResource.class);
	
	UnitService unitService = new UnitService();
    
    @POST
    public Response addUnit(@PathParam("apartmentId") Long apartmentId,
    		Unit unit, @Context UriInfo uriInfo) 
    {
    	Unit newUnit = unitService.addUnit(unit);
    	URI uri = uriInfo.getAbsolutePathBuilder().path(newUnit.getUnitId().toString()).build();
    	return Response.created(uri).entity(newUnit).build();
    }
	
	@GET
    public Response getAllUnits(@PathParam("apartmentId") Long apartmentId) 
	{
    	List<Unit> unitList = unitService.getAllUnitsOfApartment(apartmentId);
    	if(unitList==null || unitList.size()==0)
    	{
    		Exception e = new RuntimeException("no unit found ");
			throw new DataNotFoundException(e.getMessage(), e);
    	}
    	final GenericEntity<List<Unit>> entity = new GenericEntity<List<Unit>>(unitList) {};
    	return Response.ok().entity(entity).build();
    }
	
	@GET
	@Path("/tree")
    public Response getUnitTree(@PathParam("apartmentId") Long apartmentId) 
	{
    	Collection<Unit> unitTree = unitService.getUnitsTreeOfApartment(apartmentId);
    	if(unitTree==null || unitTree.size()==0)
    	{
    		Exception e = new RuntimeException("no unit found ");
			throw new DataNotFoundException(e.getMessage(), e);
    	}
    	final GenericEntity<Collection<Unit>> entity = new GenericEntity<Collection<Unit>>(unitTree) {};
    	return Response.ok().entity(entity).build();
    }
	
	@GET
    @Path("/{unitId}")
    public Response getUnit(@PathParam("apartmentId") Long apartmentId,
    		@PathParam("unitId") Long unitId) 
	{
    	Unit unit = unitService.getUnit(unitId);
    	if(unit==null)
    	{
    		Exception e = new RuntimeException("unit not found for Id: " + unitId);
			throw new DataNotFoundException(e.getMessage(), e);
    	}
    	return Response.ok().entity(unit).build();
    }
	
	@DELETE
    @Path("{unitId}")
    public Response deleteUnit(@PathParam("apartmentId") Long apartmentId,
    		@PathParam("unitId") Long unitId) 
	{
		logger.debug("removing unit " + unitId + " " + unitService.getUnit(unitId));
		Unit deletedUnit = unitService.removeUnit(unitId);
    	return Response.ok().entity(deletedUnit).build();
    }
	
	@Path("{unitId}/profile")
    public ProfileResource getProfileResource()
    {
    	return new ProfileResource();
    }
}

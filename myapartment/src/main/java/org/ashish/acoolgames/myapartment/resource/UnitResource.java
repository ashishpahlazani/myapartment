package org.ashish.acoolgames.myapartment.resource;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.ashish.acoolgames.myapartment.exception.DataNotFoundException;
import org.ashish.acoolgames.myapartment.model.Unit;
import org.ashish.acoolgames.myapartment.service.UnitService;


@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UnitResource 
{
	UnitService unitService = new UnitService();
	
	@DELETE
    @Path("{unitId}")
    public Unit deleteUnit(@PathParam("apartmentId") Integer apartmentId,
    		@PathParam("unitId") Integer unitId) {
    	System.out.println("removing unit " + unitId + " " + unitService.getUnit(unitId));
        return unitService.removeUnit(unitId);
    }
    
    @POST
    public Unit addUnit(Unit unit, @Context UriInfo uriInfo) {
    	//Unit newMessage = unitService.addUnit(unit);
    	//URI uri = uriInfo.getAbsolutePathBuilder().path(newMessage.getUnitId()).build();
    	//return Response.created(uri).entity(newMessage).build();
        return unitService.addUnit(unit);
    }
	
	@GET
    public List<Unit> getAllUnits(@PathParam("apartmentId") Integer apartmentId) 
	{
    	List<Unit> unitList = unitService.getAllUnitsOfApartment(apartmentId);
    	if(unitList == null)
    	{
    		throw new DataNotFoundException("Apartment ID : " + apartmentId + " Not Found");
    	}
        return unitList;
    }
	
	@GET
	@Path("/tree")
    public Collection<Unit> getUnitTree(@PathParam("apartmentId") Integer apartmentId) 
	{
    	Collection<Unit> unitTree = unitService.getUnitsTreeOfApartment(apartmentId);
    	if(unitTree == null)
    	{
    		throw new DataNotFoundException("Apartment ID : " + apartmentId + " Not Found");
    	}
        return unitTree;
    }
	
	@GET
    @Path("/{unitId}")
    public Unit getUnit(@PathParam("apartmentId") Integer apartmentId,
    		@PathParam("unitId") Integer unitId) 
	{
    	Unit unit = unitService.getUnit(unitId);
    	if(unit == null)
    	{
    		throw new DataNotFoundException("Unit ID : " + unitId + " Not Found");
    	}
        return unit;
    }
}

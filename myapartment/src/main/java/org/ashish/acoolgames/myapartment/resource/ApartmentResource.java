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
import org.ashish.acoolgames.myapartment.model.Apartment;
import org.ashish.acoolgames.myapartment.service.ApartmentService;

@Path("apartment")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ApartmentResource {
	private static final Logger logger = Logger.getLogger(ApartmentResource.class);
	
	ApartmentService apartmentService = new ApartmentService();
    
    @POST
    public Response addApartment(Apartment apartment, @Context UriInfo uriInfo) {
    	Apartment responseApartment = apartmentService.addApartment(apartment);
    	URI uri = uriInfo.getAbsolutePathBuilder().path(responseApartment.getApartmentId().toString()).build();
    	return Response.created(uri).entity(responseApartment).build();
    }
	
	@GET
    public Response getAllApartment() {
		List<Apartment> apartmentList = apartmentService.getAllApartment();
		if(apartmentList==null || apartmentList.size()==0)
    	{
    		Exception e = new RuntimeException("no apartment found ");
			throw new DataNotFoundException(e.getMessage(), e);
    	}
		final GenericEntity<List<Apartment>> entity = new GenericEntity<List<Apartment>>(apartmentList) {};
    	return Response.ok().entity(entity).build();
    }
	
	@GET
    @Path("{apartmentId}")
    public Response getApartment(@PathParam("apartmentId") Long apartmentId) {
		Apartment apartment = apartmentService.getApartment(apartmentId);
		if(apartment==null)
    	{
			Exception e = new RuntimeException("Apartment not found for Id: " + apartmentId);
			throw new DataNotFoundException(e.getMessage(), e);
    	}
    	return Response.ok().entity(apartment).build();
    }
	
	@DELETE
    @Path("{apartmentId}")
    public Response deleteApartment(@PathParam("apartmentId") Long apartmentId) {
    	logger.debug("removning apartment " + apartmentId + " " + apartmentService.getApartment(apartmentId));
    	Apartment apartment = apartmentService.removeApartment(apartmentId);
    	return Response.ok().entity(apartment).build();
    }
	
    @Path("{apartmentId}/units")
    public UnitResource getUnitResource()
    {
    	return new UnitResource();
    }
}

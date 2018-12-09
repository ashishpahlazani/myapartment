package org.ashish.acoolgames.myapartment.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ashish.acoolgames.myapartment.exception.DataNotFoundException;
import org.ashish.acoolgames.myapartment.model.Apartment;
import org.ashish.acoolgames.myapartment.service.ApartmentService;

@Path("apartment")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ApartmentResource {
	ApartmentService apartmentService = new ApartmentService();
	
	@DELETE
    @Path("{apartmentId}")
    public Apartment deleteApartment(@PathParam("apartmentId") Integer apartmentId) {
    	System.out.println("removning apartment " + apartmentId + " " + apartmentService.getApartment(apartmentId));
        return apartmentService.removeApartment(apartmentId);
    }
    
    @POST
    public Apartment addApartment(Apartment apartment) {
    	//Unit newMessage = unitService.addUnit(unit);
    	//URI uri = uriInfo.getAbsolutePathBuilder().path(newMessage.getUnitId()).build();
    	//return Response.created(uri).entity(newMessage).build();
        return apartmentService.addApartment(apartment);
    }
	
	@GET
    public Apartment getAllApartment() {
		Apartment unit = apartmentService.getApartment(1);
    	if(unit == null)
    	{
    		throw new DataNotFoundException("getApartment ID : " + 1 + " Not Found");
    	}
        return unit;
    }
	
	@GET
    @Path("{apartmentId}")
    public Apartment getApartment(@PathParam("apartmentId") Integer apartmentId) {
		Apartment apartment = apartmentService.getApartment(apartmentId);
    	if(apartment == null)
    	{
    		throw new DataNotFoundException("getApartment ID : " + apartmentId + " Not Found");
    	}
        return apartment;
    }
	
    @Path("{apartmentId}/units")
    public UnitResource getUnitResource()
    {
    	return new UnitResource();
    }
}

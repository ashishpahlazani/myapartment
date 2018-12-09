package org.ashish.acoolgames.myapartment.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.ashish.acoolgames.myapartment.model.ErrorMessage;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException>{
	
	@Override
	public Response toResponse(DataNotFoundException ex) {
		System.out.println("inside DataNotFoundExceptionMapper");
		ErrorMessage errorMessage = new ErrorMessage(404, ex.getMessage(), ex.getMessage());
		return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
	}
}

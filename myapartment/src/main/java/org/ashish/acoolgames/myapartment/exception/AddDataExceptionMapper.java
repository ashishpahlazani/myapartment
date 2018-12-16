package org.ashish.acoolgames.myapartment.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.ashish.acoolgames.myapartment.model.ErrorMessage;

@Provider
public class AddDataExceptionMapper implements ExceptionMapper<AddDataException>{
	
	@Override
	public Response toResponse(AddDataException ex) {
		System.out.println("inside DataNotFoundExceptionMapper");
		ErrorMessage errorMessage = new ErrorMessage(404, ex.getMessage(), ex.getCause().toString());
		return Response.status(Status.BAD_REQUEST).entity(errorMessage).build();
	}
}

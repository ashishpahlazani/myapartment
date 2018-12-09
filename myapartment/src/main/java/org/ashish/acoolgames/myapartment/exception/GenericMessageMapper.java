package org.ashish.acoolgames.myapartment.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.ashish.acoolgames.myapartment.model.ErrorMessage;

@Provider
public class GenericMessageMapper implements ExceptionMapper<Throwable> {
	
	@Override
	public Response toResponse(Throwable ex) {
		ErrorMessage errorMessage = new ErrorMessage(500, ex.getMessage(), ex.getMessage());
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
	}
}

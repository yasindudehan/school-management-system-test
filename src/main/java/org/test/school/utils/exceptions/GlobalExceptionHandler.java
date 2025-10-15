package org.test.school.utils.exceptions;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.test.school.dto.ErrorResponse;


@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable ex) {
             int statusCode=500;
             String message=ex.getMessage();
             if(ex instanceof APIException){
                 statusCode= ((APIException) ex).getStatusCode();
                 message= ((APIException) ex).getMessage();
             }
             else if(ex instanceof NotFoundException){
                 message="url not found";
                 statusCode=404;
             }else if(ex instanceof NotAllowedException){
                 message="url not allowed";
                 statusCode=405;
             }
             ErrorResponse errorResponse=new ErrorResponse();
             errorResponse.setErrorCode(String.valueOf(statusCode));
             errorResponse.setMessage(message);
             return Response.status(statusCode).entity(errorResponse).build();
    }
}

package org.test.school.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.test.school.dto.ResponseWrapper;
import org.test.school.dto.UserLoginRequest;
import org.test.school.services.LoginService;
import org.test.school.utils.exceptions.APIException;

@Path("/users")
public class LoginController {

    @Inject
    LoginService loginService;


    @POST
    @Path("/login")
    @PermitAll
    public Response login(UserLoginRequest request) throws APIException, JsonProcessingException {
        ResponseWrapper responseWrapper = loginService.login(request);
        return Response.ok(responseWrapper).build();
    }
}

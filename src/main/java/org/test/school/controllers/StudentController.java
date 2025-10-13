package org.test.school.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.test.school.dto.ResponseWrapper;
import org.test.school.dto.StudentRequest;
import org.test.school.repositories.models.Student;
import org.test.school.services.StudentService;
import jakarta.inject.Inject;
import org.test.school.utils.exceptions.APIException;

import java.util.List;

@Path("/students")
@Consumes("application/json")
@Produces("application/json")

public class StudentController {


     @Inject
     StudentService studentService;

    @POST
    @Path("/register")
    @RolesAllowed("ADMIN")
    public Response registerStudent(@Valid StudentRequest request) throws APIException {
         ResponseWrapper<Student> responseWrapper= studentService.registerStudent(request);
         return Response.ok(responseWrapper).build();
    }

    @GET
    @Path("/all")
    @RolesAllowed({"ADMIN","LECTURER"})
    public Response getAllStudents(@QueryParam("page") int page, @QueryParam("size") int size) throws APIException {
        ResponseWrapper<List<Student>> responseWrapper = studentService.getAllStudents(page,size);
        return Response.ok(responseWrapper).build();
    }
    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN","LECTURER","STUDENT"})
    public Response getStudentById(@PathParam("id") int id) throws APIException {
        ResponseWrapper<Student> responseWrapper = studentService.getStudentById(id);
        return Response.ok(responseWrapper).build();
    }
}

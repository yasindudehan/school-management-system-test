package org.test.school.controllers;


import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.test.school.dto.CourseRequest;
import org.test.school.dto.ResponseWrapper;
import org.test.school.repositories.models.Course;
import org.test.school.services.CourseService;
import org.test.school.utils.exceptions.APIException;

import java.util.List;

@Path("/courses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class CourseController {


    @Inject
    CourseService courseService;


    @POST
    @Path("/add")
    @RolesAllowed("ADMIN")
    public Response addCourse(@Valid CourseRequest courseRequest) throws APIException {
               ResponseWrapper<Course> responseWrapper=courseService.addCourse(courseRequest);
               return Response.ok(responseWrapper).build();
    }

    @GET
    @Path("/all")
    @RolesAllowed("ADMIN")
    public Response getAllCourses(@QueryParam("page") int page, @QueryParam("size") int size) throws APIException {
        ResponseWrapper<List<Course>> responseWrapper = courseService.getAllCourses(page,size);
        return Response.ok(responseWrapper).build();
    }
    @GET
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response getCourseById(@PathParam("id") int id) throws APIException {
        ResponseWrapper<Course> responseWrapper = courseService.getCourseById(id);
        return Response.ok(responseWrapper).build();
    }
}

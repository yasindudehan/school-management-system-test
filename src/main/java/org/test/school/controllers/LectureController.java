package org.test.school.controllers;


import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.test.school.dto.LectureRequest;
import org.test.school.dto.ResponseWrapper;
import org.test.school.repositories.models.Lecturer;
import org.test.school.services.LectureService;
import org.test.school.utils.exceptions.APIException;

import java.util.List;

@Path("/lecture")
@Consumes("application/json")
@Produces("application/json")
public class LectureController {


    @Inject
    LectureService lectureService;

    @POST
    @Path("/add")
    @RolesAllowed("ADMIN")
    public Response addLecture(@Valid LectureRequest lectureRequest) throws APIException {
        ResponseWrapper<Lecturer> responseWrapper = lectureService.addLecture(lectureRequest);
        return Response.ok(responseWrapper).build();
    }
    @GET
    @Path("/all")
    @RolesAllowed("ADMIN")
    public Response getAllLectures(@QueryParam("page") int page, @QueryParam("size") int size) throws APIException {
        ResponseWrapper<List<Lecturer>> responseWrapper = lectureService.getAllLectures(page,size);
        return Response.ok(responseWrapper).build();
    }
    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN","LECTURER"})
    public Response getLectureById(@PathParam("id") int id) throws APIException {
        ResponseWrapper<Lecturer> responseWrapper = lectureService.getLectureById(id);
        return Response.ok(responseWrapper).build();
    }
}

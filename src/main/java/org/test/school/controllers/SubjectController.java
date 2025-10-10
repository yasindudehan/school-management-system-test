package org.test.school.controllers;


import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.test.school.dto.ResponseWrapper;
import org.test.school.dto.SubjectRequest;
import org.test.school.repositories.models.Subject;
import org.test.school.services.SubjectService;
import org.test.school.utils.exceptions.APIException;

import java.util.List;

@Path("/subject")
@Consumes("application/json")
@Produces("application/json")
public class SubjectController {

    @Inject
    SubjectService subjectService;

    @POST
    @Path("/add")
    public Response addSubject(@Valid SubjectRequest subjectRequest) throws APIException {
        ResponseWrapper<Subject> responseWrapper = subjectService.addSubject(subjectRequest);
        return Response.ok(responseWrapper).build();
    }
    @GET
    @Path("/all")
    public Response getAllSubjects(@QueryParam("page") int page, @QueryParam("size") int size) throws APIException {
        ResponseWrapper<List<Subject>> responseWrapper = subjectService.getAllSubjects(page,size);
        return Response.ok(responseWrapper).build();
    }
    @GET
    @Path("/{id}")
    public Response getSubjectById(@PathParam("id") int id) throws APIException {
        ResponseWrapper<Subject> responseWrapper = subjectService.getSubjectById(id);
        return Response.ok(responseWrapper).build();
    }
}

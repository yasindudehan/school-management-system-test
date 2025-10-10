package org.test.school.controllers;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.test.school.dto.MarkRequest;
import org.test.school.dto.ResponseWrapper;
import org.test.school.repositories.models.Marks;
import org.test.school.services.MarkService;
import org.test.school.utils.exceptions.APIException;

import java.util.List;

@Path("/marks")
@Consumes("application/json")
@Produces("application/json")
public class MarksController {
    @Inject
    MarkService markService;


    @POST
    @Path("/add")
    public Response addMark(MarkRequest markRequest) throws APIException {
        ResponseWrapper<Marks> responseWrapper = markService.addMark(markRequest);
        return Response.ok(responseWrapper).build();
    }

    @GET
    @Path("/all")
    public Response getAllMarks(@QueryParam("page") int page,@QueryParam("size") int size) throws APIException {
        ResponseWrapper<List<Marks>> responseWrapper = markService.getAllMarks(page, size);
        return Response.ok(responseWrapper).build();
    }

}

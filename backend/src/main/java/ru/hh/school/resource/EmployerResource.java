package ru.hh.school.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import ru.hh.school.service.EmployerOuterService;
import ru.hh.school.util.json.views.EmployerViews;
import ru.hh.school.util.Pagination;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/employer")
public class EmployerResource {

    private final EmployerOuterService outerService;

    public EmployerResource(EmployerOuterService outerService) {
        this.outerService = outerService;
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(EmployerViews.Short.class)
    public Response getEmployers(@QueryParam("query") String query, @QueryParam("page") Integer page, @QueryParam("per_page") Integer perPage) throws EmployerOuterService.OuterAPIException, JsonProcessingException {
        return Response.ok(outerService.getEmployers(query, new Pagination(page, perPage))).build();
    }


    @GET
    @Path("/{employerId}")
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(EmployerViews.Detailed.class)
    public Response getEmployer(@PathParam("employerId") Long employerId) throws EmployerOuterService.OuterAPIException, JsonProcessingException {
        return Response.ok(outerService.getEmployer(employerId)).build();
    }
}

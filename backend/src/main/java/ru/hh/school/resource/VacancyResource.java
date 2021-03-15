package ru.hh.school.resource;

import ru.hh.school.exceptions.HhApiException;
import ru.hh.school.service.VacancyOuterService;
import ru.hh.school.util.Pagination;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/vacancy")
public class VacancyResource {
    private final VacancyOuterService service;

    public VacancyResource(VacancyOuterService service) {
        this.service = service;
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVacancies(@QueryParam("query") String query, @QueryParam("page") Integer page, @QueryParam("per_page") Integer perPage) throws HhApiException {
        return Response.ok(service.getVacancies(query, new Pagination(page, perPage))).build();
    }

    @GET
    @Path("/{vacancyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVacancy(@PathParam("vacancyId") Long id) throws HhApiException {
        return Response.ok(service.getVacancy(id)).build();
    }

}

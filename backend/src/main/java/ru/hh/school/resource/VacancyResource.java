package ru.hh.school.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import ru.hh.school.service.AbstractOuterEntityGetter;
import ru.hh.school.service.VacancyOuterService;
import ru.hh.school.util.Pagination;
import ru.hh.school.util.json.views.CombinationViews;

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
    @JsonView(CombinationViews.EmployerShortAndVacancyShort.class)
    public Response getVacancies(@QueryParam("query") String query, @QueryParam("page") Integer page, @QueryParam("per_page") Integer perPage) throws AbstractOuterEntityGetter.OuterAPIException, JsonProcessingException {
        return Response.ok(service.getVacancies(query, new Pagination(page, perPage))).build();
    }

    @GET
    @Path("/{vacancyId}")
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(CombinationViews.EmployerShortAndVacancyDetailed.class)
    public Response getVacancy(@PathParam("vacancyId") Long id) throws AbstractOuterEntityGetter.OuterAPIException, JsonProcessingException {
        return Response.ok(service.getVacancy(id)).build();
    }

}

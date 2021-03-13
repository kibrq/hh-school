package ru.hh.school.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import ru.hh.school.entity.Employer;
import ru.hh.school.service.EmployerOuterService;
import ru.hh.school.service.GenericFavoritesService;
import ru.hh.school.util.json.views.EmployerViews;
import ru.hh.school.util.Pagination;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("/favorites/employer")
public class FavoritesEmployerResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(FavoritesEmployerResource.class);

    private final EmployerOuterService outerService;
    private final GenericFavoritesService favoritesService;

    public FavoritesEmployerResource(EmployerOuterService outerService, @Qualifier("employerService") GenericFavoritesService favoritesService) {
        this.outerService = outerService;
        this.favoritesService = favoritesService;
    }

    private static class CreateRequestBody {
        @JsonProperty(value = "employer_id")
        public Long employerId;
        @JsonProperty
        public String comment;
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createFavoriteEmployer(CreateRequestBody request) throws EmployerOuterService.OuterAPIException, JsonProcessingException {
        favoritesService.post(
                outerService.getEmployer(request.employerId),
                request.comment
        );
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(EmployerViews.FavoriteDetailed.class)
    public Response getAllFavoriteEmployers(@QueryParam("page") Integer page, @QueryParam("per_page") Integer perPage) {
        return Response.ok(favoritesService.getEntities(new Pagination(page, perPage), Employer.class)).build();
    }

    private static class UpdateRequestBody {
        @JsonProperty
        String comment;
    }

    @PUT
    @Path("/{employerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateEmployer(@PathParam("employerId") Long employerId, UpdateRequestBody request) {
        favoritesService.update(employerId, request.comment, Employer.class);
    }

    @DELETE
    @Path("/{employerId}")
    public void deleteEmployer(@PathParam("employerId") Long employerId) {
        favoritesService.delete(employerId, Employer.class);
    }

    @POST
    @Path("/{employerId}/refresh")
    public void refreshEmployer(@PathParam("employerId") Long employerId) throws EmployerOuterService.OuterAPIException, JsonProcessingException {
        favoritesService.refresh(
                outerService.getEmployer(employerId)
        );
    }

}

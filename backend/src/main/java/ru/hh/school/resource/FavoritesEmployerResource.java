package ru.hh.school.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.school.service.EmployerFavoritesService;
import ru.hh.school.service.EmployerOuterService;
import ru.hh.school.util.EmployerViews;
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
    private final EmployerFavoritesService favoritesService;

    public FavoritesEmployerResource(EmployerOuterService outerService, EmployerFavoritesService favoritesService) {
        this.outerService = outerService;
        this.favoritesService = favoritesService;
    }

    public static class CreateRequestBody {
        @JsonProperty(value = "employer_id")
        public Long employerId;
        @JsonProperty
        public String comment;
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createFavoriteEmployer(CreateRequestBody request) throws EmployerOuterService.OuterAPIException, JsonProcessingException {
        favoritesService.postEmployer(
                outerService.getFullEmployer(request.employerId),
                request.comment
        );
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(EmployerViews.FavoriteDetailed.class)
    public Response getAllFavoriteEmployers(@QueryParam("page") Integer page, @QueryParam("per_page") Integer perPage) {
        return Response.ok(favoritesService.getEmployers(new Pagination(page, perPage))).build();
    }

    public static class UpdateRequestBody {
        @JsonProperty
        String comment;
    }

    @PUT
    @Path("/{employerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateEmployer(@PathParam("employerId") Long employerId, UpdateRequestBody request) {
        favoritesService.updateEmployer(employerId, request.comment);
    }

    @DELETE
    @Path("/{employerId}")
    public void deleteEmployer(@PathParam("employerId") Long employerId) {
        favoritesService.deleteEmployer(employerId);
    }

    @POST
    @Path("/{employerId}/refresh")
    public void refreshEmployer(@PathParam("employerId") Long employerId) throws EmployerOuterService.OuterAPIException, JsonProcessingException {
        favoritesService.refreshEmployer(
                outerService.getFullEmployer(employerId)
        );
    }

}

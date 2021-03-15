package ru.hh.school.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.hh.school.dto.EmployerOuterDtoDetailed;
import ru.hh.school.exceptions.HhApiException;
import ru.hh.school.service.EmployerOuterService;
import ru.hh.school.service.FavoritesEmployerService;
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
    private final FavoritesEmployerService innerService;

    public FavoritesEmployerResource(EmployerOuterService outerService, FavoritesEmployerService innerService) {
        this.outerService = outerService;
        this.innerService = innerService;
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
    public void createFavoriteEmployer(CreateRequestBody request) throws HhApiException {
        EmployerOuterDtoDetailed employerDto = outerService.getEmployer(request.employerId);
        innerService.postEmployer(employerDto, request.comment);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFavoriteEmployers(@QueryParam("page") Integer page, @QueryParam("per_page") Integer perPage) {
        return Response.ok(innerService.getEmployers(new Pagination(page, perPage))).build();
    }

    private static class UpdateRequestBody {
        @JsonProperty
        String comment;
    }

    @PUT
    @Path("/{employerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateEmployer(@PathParam("employerId") Long employerId, UpdateRequestBody request) {
        innerService.updateComment(employerId, request.comment);
    }

    @DELETE
    @Path("/{employerId}")
    public void deleteEmployer(@PathParam("employerId") Long employerId) {
        innerService.delete(employerId);
    }

    @POST
    @Path("/{employerId}/refresh")
    public void refreshEmployer(@PathParam("employerId") Long employerId) throws HhApiException {
        EmployerOuterDtoDetailed employerDto = outerService.getEmployer(employerId);
        innerService.refresh(employerDto);
    }

}

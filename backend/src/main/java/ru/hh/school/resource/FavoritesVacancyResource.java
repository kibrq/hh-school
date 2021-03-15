package ru.hh.school.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

import ru.hh.school.dto.EmployerOuterDtoDetailed;
import ru.hh.school.dto.VacancyOuterDtoDetailed;
import ru.hh.school.exceptions.FavoritesException;
import ru.hh.school.exceptions.HhApiException;
import ru.hh.school.service.EmployerOuterService;
import ru.hh.school.service.FavoritesVacancyService;
import ru.hh.school.service.VacancyOuterService;
import ru.hh.school.util.Pagination;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("favorites/vacancy")
public class FavoritesVacancyResource {

    private final VacancyOuterService vacancyOuterService;
    private final EmployerOuterService employerOuterService;
    private final FavoritesVacancyService service;

    public FavoritesVacancyResource(VacancyOuterService vacancyOuterService, EmployerOuterService employerOuterService, FavoritesVacancyService service) {
        this.vacancyOuterService = vacancyOuterService;
        this.employerOuterService = employerOuterService;
        this.service = service;
    }

    private static class CreateRequestBody {
        @JsonProperty(value = "vacancy_id")
        public Long vacancyId;
        @JsonProperty
        public String comment;
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createFavoriteVacancy(CreateRequestBody request) throws HhApiException {
        VacancyOuterDtoDetailed vacancyDto = vacancyOuterService.getVacancy(request.vacancyId);
        EmployerOuterDtoDetailed employerDto = employerOuterService.getEmployer(vacancyDto.getEmployer().getId());
        service.postVacancy(vacancyDto, employerDto, request.comment);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFavoriteVacancies(@QueryParam("page") Integer page, @QueryParam("per_page") Integer perPage) {
        return Response.ok(service.getVacancies(new Pagination(page, perPage))).build();
    }

    private static class UpdateRequestBody {
        @JsonProperty
        String comment;
    }

    @PUT
    @Path("/{vacancyId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateVacancy(@PathParam("vacancyId") Long vacancyId, UpdateRequestBody request) {
        service.updateComment(vacancyId, request.comment);
    }

    @DELETE
    @Path("/{vacancyId}")
    public void deleteVacancy(@PathParam("vacancyId") Long vacancyId) {
        service.delete(vacancyId);
    }

    @POST
    @Path("/{vacancyId}/refresh")
    public void refreshEmployer(@PathParam("vacancyId") Long vacancyId) throws HhApiException {
        VacancyOuterDtoDetailed vacancyDto = vacancyOuterService.getVacancy(vacancyId);
        service.refresh(vacancyDto);
    }
}

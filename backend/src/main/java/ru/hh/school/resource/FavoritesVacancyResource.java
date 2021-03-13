package ru.hh.school.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Qualifier;
import ru.hh.school.entity.Employer;
import ru.hh.school.entity.Vacancy;
import ru.hh.school.service.EmployerOuterService;
import ru.hh.school.service.GenericFavoritesService;
import ru.hh.school.service.VacancyOuterService;
import ru.hh.school.util.Pagination;
import ru.hh.school.util.json.views.CombinationViews;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("favorites/vacancy")
public class FavoritesVacancyResource {

    private final VacancyOuterService vacancyOuterService;
    private final EmployerOuterService employerOuterService;
    private final GenericFavoritesService employerFavoritesService;
    private final GenericFavoritesService vacancyFavoritesService;

    public FavoritesVacancyResource(VacancyOuterService vacancyOuterService, EmployerOuterService employerOuterService, @Qualifier("employerService") GenericFavoritesService employerFavoritesService, @Qualifier("vacancyService") GenericFavoritesService vacancyFavoritesService) {
        this.vacancyOuterService = vacancyOuterService;
        this.employerOuterService = employerOuterService;
        this.employerFavoritesService = employerFavoritesService;
        this.vacancyFavoritesService = vacancyFavoritesService;
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
    public void createFavoriteVacancy(CreateRequestBody request) throws EmployerOuterService.OuterAPIException, JsonProcessingException {
        Vacancy vacancy = vacancyOuterService.getVacancy(request.vacancyId);
        if (employerFavoritesService.getEntity(vacancy.getEmployer().getId(), Employer.class) == null) {
            employerFavoritesService.post(
                    employerOuterService.getEmployer(vacancy.getEmployer().getId()),
                    request.comment
            );
        }
        vacancyFavoritesService.post(vacancy, request.comment);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(CombinationViews.EmployerFavoriteDetailedAndVacancyFavoriteDetailed.class)
    public Response getAllFavoriteVacancies(@QueryParam("page") Integer page, @QueryParam("per_page") Integer perPage) {
        return Response.ok(vacancyFavoritesService.getEntities(new Pagination(page, perPage), Vacancy.class)).build();
    }

    private static class UpdateRequestBody {
        @JsonProperty
        String comment;
    }

    @PUT
    @Path("/{vacancyId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateEmployer(@PathParam("vacancyId") Long vacancyId, UpdateRequestBody request) {
        vacancyFavoritesService.update(vacancyId, request.comment, Vacancy.class);
    }

    @DELETE
    @Path("/{employerId}")
    public void deleteEmployer(@PathParam("employerId") Long employerId) {
        vacancyFavoritesService.delete(employerId, Vacancy.class);
    }

    @POST
    @Path("/{vacancyId}/refresh")
    public void refreshEmployer(@PathParam("vacancyId") Long vacancyId) throws EmployerOuterService.OuterAPIException, JsonProcessingException {
        vacancyFavoritesService.refresh(
                vacancyOuterService.getVacancy(vacancyId)
        );
    }
}

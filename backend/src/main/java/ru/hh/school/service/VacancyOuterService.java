package ru.hh.school.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import ru.hh.school.entity.Vacancy;
import ru.hh.school.util.Pagination;
import ru.hh.school.util.json.views.CombinationViews;

import java.util.List;

public class VacancyOuterService extends AbstractOuterEntityGetter {

    @Override
    protected String baseURL() {
        return "https://api.hh.ru/vacancies";
    }

    public List<Vacancy> getVacancies(String query, Pagination pagination) throws OuterAPIException, JsonProcessingException {
        return getEntities(query, pagination, Vacancy.class, CombinationViews.EmployerShortAndVacancyShort.class);
    }

    public Vacancy getVacancy(Long id) throws OuterAPIException, JsonProcessingException {
        return getEntity(id, Vacancy.class, CombinationViews.EmployerShortAndVacancyDetailed.class);
    }
}

package ru.hh.school.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import ru.hh.school.entity.Employer;
import ru.hh.school.util.json.views.EmployerViews;
import ru.hh.school.util.Pagination;

import java.util.List;

public class EmployerOuterService extends AbstractOuterEntityGetter {

    @Override
    protected String baseURL() {
        return "https://api.hh.ru/employers";
    }

    public List<Employer> getEmployers(String query, Pagination pagination) throws OuterAPIException, JsonProcessingException {
        return getEntities(query, pagination, Employer.class, EmployerViews.Short.class);
    }

    public Employer getEmployer(Long id) throws OuterAPIException, JsonProcessingException {
        return getEntity(id, Employer.class, EmployerViews.Detailed.class);
    }


}

package ru.hh.school.service;


import ru.hh.school.dto.VacancyOuterDtoDetailed;
import ru.hh.school.dto.VacancyOuterDtoShort;
import ru.hh.school.exceptions.HhApiException;
import ru.hh.school.util.Pagination;

import java.util.List;

public class VacancyOuterService extends AbstractOuterEntityGetter {

    @Override
    protected String baseURL() {
        return "https://api.hh.ru/vacancies";
    }

    public List<VacancyOuterDtoShort> getVacancies(String query, Pagination pagination) throws HhApiException {
        return getEntities(query, pagination, VacancyOuterDtoShort.class);
    }

    public VacancyOuterDtoDetailed getVacancy(Long id) throws HhApiException {
        return getEntity(id, VacancyOuterDtoDetailed.class);
    }
}

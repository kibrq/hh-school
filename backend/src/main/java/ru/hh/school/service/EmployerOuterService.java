package ru.hh.school.service;


import ru.hh.school.dto.EmployerOuterDtoDetailed;
import ru.hh.school.dto.EmployerOuterDtoShort;
import ru.hh.school.exceptions.HhApiException;
import ru.hh.school.util.Pagination;

import java.util.List;

public class EmployerOuterService extends AbstractOuterEntityGetter {

    @Override
    protected String baseURL() {
        return "https://api.hh.ru/employers";
    }

    public List<EmployerOuterDtoShort> getEmployers(String query, Pagination pagination) throws HhApiException {
        return getEntities(query, pagination, EmployerOuterDtoShort.class);
    }

    public EmployerOuterDtoDetailed getEmployer(Long id) throws HhApiException {
        return getEntity(id, EmployerOuterDtoDetailed.class);
    }


}

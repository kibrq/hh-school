package ru.hh.school.service;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.hh.school.dao.EmployerDao;
import ru.hh.school.dao.GenericDao;
import ru.hh.school.entity.Area;
import ru.hh.school.entity.Employer;
import ru.hh.school.util.Pagination;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

public class EmployerFavoritesService {
    private final EmployerDao dao;

    public EmployerFavoritesService(@Qualifier("employerDao") EmployerDao dao) {
        this.dao = dao;
    }

    @Transactional
    public void postEmployer(Employer employer, String comment) {
        Employer emp = dao.getById(Employer.class, employer.getId());
        Area area = dao.getById(Area.class, employer.getArea().getId());
        if (area == null) {
            dao.saveOrUpdate(employer.getArea());
        }
        if (emp != null) {
            employer = emp;
        }
        employer.refresh(comment);
        dao.saveOrUpdate(employer);
    }

    @Transactional
    public List<Employer> getEmployers(@NotNull Pagination pagination) {
        int toSkip = pagination.perPage * pagination.page, toTake = pagination.perPage;
        return dao.getAll().stream()
                .skip(toSkip).limit(toTake)
                .peek(Employer::seen)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateEmployer(@NotNull Long id, String comment) {
        Employer employer = dao.getById(Employer.class, id);
        if (employer == null) {
            throw new RuntimeException();
        }
        employer.setComment(comment);
    }

    @Transactional
    public void deleteEmployer(@NotNull Long id) {
        dao.delete(Employer.class, id);
    }

    @Transactional
    public void refreshEmployer(@NotNull Employer employer) {
        Employer emp = dao.getById(Employer.class, employer.getId());
        if (emp == null) {
            throw new RuntimeException();
        }
        emp.setName(employer.getName());
        emp.setDescription(employer.getDescription());
        emp.setArea(employer.getArea());
    }
}

package ru.hh.school.service;

import org.springframework.stereotype.Service;
import ru.hh.school.dao.GenericDao;
import ru.hh.school.entity.AbstractEntity;
import ru.hh.school.entity.Employer;
import ru.hh.school.entity.Vacancy;

import javax.transaction.Transactional;

@Service("vacancyService")
public class VacancyFavoritesService extends GenericFavoritesService {

    private final EmployerOuterService outerService;

    public VacancyFavoritesService(GenericDao dao, EmployerOuterService outerService) {
        super(dao);
        this.outerService = outerService;
    }

    @Override
    @Transactional
    public void post(AbstractEntity entity, String comment) {
        assert entity.getClass() == Vacancy.class;
        Vacancy vacancy = (Vacancy) entity;
        Vacancy existingVacancy = dao.getById(Vacancy.class, vacancy.getId());
        if (existingVacancy != null) {
            return;
        }
        vacancy.refresh(comment);
        dao.saveOrUpdate(vacancy);
    }
}

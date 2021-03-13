package ru.hh.school.service;

import org.springframework.stereotype.Service;
import ru.hh.school.dao.GenericDao;
import ru.hh.school.entity.AbstractEntity;
import ru.hh.school.entity.Employer;

import javax.transaction.Transactional;

@Service("employerService")
public class EmployerFavoritesService extends GenericFavoritesService{

    public EmployerFavoritesService(GenericDao dao) {
        super(dao);
    }

    @Override
    @Transactional
    public void post(AbstractEntity entity, String comment) {
        assert entity.getClass() == Employer.class;
        Employer employer = (Employer) entity;
        Employer existingEmployer = dao.getById(Employer.class, employer.getId());
        if (existingEmployer != null) {
            return;
        }
        employer.refresh(comment);
        dao.saveOrUpdate(employer);
    }
}

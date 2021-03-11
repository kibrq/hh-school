package ru.hh.school.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.hh.school.entity.Employer;

import java.util.List;

@Repository("employerDao")
public class EmployerDao extends GenericDao {

    public EmployerDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Employer> getAll() {
        return session().createQuery("select e from Employer e", Employer.class).getResultList();
    }
}

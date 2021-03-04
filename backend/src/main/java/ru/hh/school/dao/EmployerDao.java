package ru.hh.school.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.hh.nab.hibernate.NabHibernateCommonConfig;
import ru.hh.school.config.ProdConfig;
import ru.hh.school.entity.Employer;

import javax.transaction.Transactional;


@Repository
public class EmployerDao {
    private final SessionFactory sessionFactory;

    public EmployerDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Transactional
    public void createEmployer() {
        Employer employer = new Employer(10L, "sasha");
        sessionFactory.getCurrentSession().save(employer);
    }


}

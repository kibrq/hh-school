package ru.hh.school.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository("genericDao")
public class GenericDao {
    protected final SessionFactory sessionFactory;

    public GenericDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public <T> T getById(Class<T> clazz, Long id) {
        return session().get(clazz, id);
    }

    public <T> void delete(Class<T> clazz, Long id) {
        session().delete(getById(clazz, id));
    }

    public void saveOrUpdate(Object object) {
        if (object == null) {
            return;
        }
        session().saveOrUpdate(object);
    }

    protected Session session() {
        return sessionFactory.getCurrentSession();
    }
}

package ru.hh.school.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public <T> List<T> getAll(Class<T> clazz) {
        String name = clazz.getName();
        name = name.substring(name.lastIndexOf('.') + 1);
        return session().createQuery("select e from " + name + " e", clazz).getResultList();
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

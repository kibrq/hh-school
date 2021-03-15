package ru.hh.school.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.hh.school.entity.AbstractEntity;
import ru.hh.school.exceptions.FavoritesException;

import java.util.function.Supplier;
import java.util.stream.Stream;

@Repository("genericDao")
public class GenericDao {
    protected final SessionFactory sessionFactory;

    public GenericDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public <T> T getEntity(Long id, Class<T> clazz) {
        return session().get(clazz, id);
    }

    public <T> void delete(Long id, Class<T> clazz) {
        session().delete(getEntity(id, clazz));
    }

    public <T> T postEntity(Long id, Supplier<T> supplier, Class<T> clazz) {
        T entity = getEntity(id, clazz);
        if (entity != null) {
            return entity;
        }
        entity = supplier.get();
        saveOrUpdate(entity);
        return entity;
    }

    public <T extends AbstractEntity> T getEntityThrowing(Long id, Class<T> clazz) throws FavoritesException {
        T entity = getEntity(id, clazz);
        if (entity == null) {
            throw new FavoritesException("There is no such id");
        }
        return entity;
    }

    public <T> Stream<T> getAll(Class<T> clazz) {
        String name = clazz.getName();
        name = name.substring(name.lastIndexOf('.') + 1);
        return session().createQuery("select e from " + name + " e", clazz).getResultList().stream();
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

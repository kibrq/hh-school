package ru.hh.school.service;

import com.sun.istack.NotNull;
import ru.hh.school.dao.GenericDao;
import ru.hh.school.entity.AbstractEntity;
import ru.hh.school.util.Pagination;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

public abstract class GenericFavoritesService {
    protected final GenericDao dao;

    public GenericFavoritesService(GenericDao dao) {
        this.dao = dao;
    }

    public abstract void post(AbstractEntity entity, String comment) ;

    @Transactional
    public <T> T getEntity(Long id, Class<T> clazz) {
        return dao.getById(clazz, id);
    }

    @Transactional
    public <T extends AbstractEntity> List<T> getEntities(@NotNull Pagination pagination, Class<T> clazz) {
        int toSkip = pagination.perPage * pagination.page, toTake = pagination.perPage;
        return dao.getAll(clazz).stream()
                .skip(toSkip).limit(toTake)
                .peek(AbstractEntity::seen)
                .collect(Collectors.toList());
    }

    @Transactional
    public <T extends AbstractEntity> void update(@NotNull Long id, String comment, Class<T> clazz) {
        T entity = dao.getById(clazz, id);
        if (entity == null) {
            throw new RuntimeException();
        }
        entity.setComment(comment);
    }

    @Transactional
    public <T> void delete(@NotNull Long id, Class<T> clazz) {
        dao.delete(clazz, id);
    }

    @Transactional
    public void refresh(@NotNull AbstractEntity entity) {
        AbstractEntity e = dao.getById(entity.getClass(), entity.getId());
        if (e == null) {
            throw new RuntimeException();
        }
        e.refresh(entity);
    }
}

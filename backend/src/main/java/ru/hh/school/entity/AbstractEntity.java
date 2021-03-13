package ru.hh.school.entity;

public abstract class AbstractEntity implements HavingArea, Favorable {
    public abstract Long getId();

    public abstract void setId(Long id);

    private boolean canRefresh(AbstractEntity entity) {
        return entity.getClass() == getClass();
    }

    public final void refresh(AbstractEntity entity) {
        if (!canRefresh(entity)) {
            return;
        }
        refreshImpl(entity);
    }

    protected abstract void refreshImpl(AbstractEntity entity);

}

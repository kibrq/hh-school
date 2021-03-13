package ru.hh.school.entity;

import java.time.LocalDateTime;

public interface Favorable {
    int getViewsCount();

    void setViewsCount(int viewsCount);

    void setComment(String comment);

    void setCreateDate(LocalDateTime createDate);

    LocalDateTime getCreateDate();

    String getComment();

    default void seen() {
        setViewsCount(getViewsCount() + 1);
    }

    default String getPopularity() {
        return Popularity.getByViews(getViewsCount()).name();
    }

    default void refresh(String comment) {
        setCreateDate(LocalDateTime.now());
        setComment(comment);
    }
}

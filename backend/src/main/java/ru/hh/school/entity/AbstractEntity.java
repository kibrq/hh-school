package ru.hh.school.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @Column(name = "id")
    protected Long id;
    @Column(name = "name")
    protected String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "area_id")
    protected Area area;

    @Column(name = "date_create")
    protected LocalDateTime dateCreate;

    @Column(name = "views_count")
    protected int viewsCount;

    @Column(name = "comment")
    protected String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void seen() {
        viewsCount += 1;
    }

    public void post(String comment) {
        this.dateCreate = LocalDateTime.now();
        this.comment = comment;
    }
}

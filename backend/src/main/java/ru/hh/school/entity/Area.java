package ru.hh.school.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.hh.school.dto.AreaOuterDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "area")
public class Area {
    @Id
    @Column(name = "area_id")
    private Long id;

    @Column(name = "name")
    private String name;

    protected Area() {
    }

    public Area(AreaOuterDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
    }

    public void refresh(AreaOuterDto dto) {
        this.name = dto.getName();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

package ru.hh.school.entity;

import ru.hh.school.dto.EmployerOuterDtoDetailed;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "employer")
public class Employer extends AbstractEntity {

    private static final int MAX_DESCRIPTION_LENGTH = 256;

    @Column(name = "description")
    private String description;

    protected Employer() {
    }

    public Employer(EmployerOuterDtoDetailed dto, Area area, String comment) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.area = area;
        this.post(comment);
    }

    public void refresh(EmployerOuterDtoDetailed dto) {
        name = dto.getName();
        description = dto.getDescription();
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

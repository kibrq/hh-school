package ru.hh.school.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.hh.school.entity.Area;

public class AreaInnerDto {
    private final Long id;
    private final String name;

    public AreaInnerDto(Area area) {
        this.id = area.getId();
        this.name = area.getName();
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

}

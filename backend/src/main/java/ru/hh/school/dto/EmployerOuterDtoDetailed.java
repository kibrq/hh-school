package ru.hh.school.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployerOuterDtoDetailed extends EmployerOuterDtoShort {
    protected AreaOuterDto area;
    protected String description;

    public EmployerOuterDtoDetailed() {}

    public EmployerOuterDtoDetailed(Long id, String name, AreaOuterDto area, String description) {
        super(id, name);
        this.area = area;
        this.description = description;
    }

    @JsonProperty("area")
    public AreaOuterDto getArea() {
        return area;
    }

    @JsonProperty("area")
    public void setArea(AreaOuterDto area) {
        this.area = area;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description.substring(0, 256);
    }
}

package ru.hh.school.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AreaOuterDto {
    private Long id;
    private String name;

    public AreaOuterDto() {}

    public AreaOuterDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty(value = "id")
    public Long getId() {
        return id;
    }

    @JsonProperty(value = "id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty(value = "name")
    public String getName() {
        return name;
    }

    @JsonProperty(value = "name")
    public void setName(String name) {
        this.name = name;
    }
}

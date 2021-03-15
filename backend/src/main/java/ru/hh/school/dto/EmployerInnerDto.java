package ru.hh.school.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.hh.school.entity.Employer;
import ru.hh.school.util.PopularityDecider;
import ru.hh.school.util.json.serializers.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class EmployerInnerDto {
    private final Long id;
    private final String name;
    private final AreaInnerDto area;
    private final String description;
    private final LocalDateTime dateCreate;
    private final String comment;
    private final int viewsCount;
    private final String popularity;

    public EmployerInnerDto(Employer employer, PopularityDecider decider) {
        id = employer.getId();
        name = employer.getName();
        area = new AreaInnerDto(employer.getArea());
        description = employer.getDescription();
        dateCreate = employer.getDateCreate();
        comment = employer.getComment();
        viewsCount = employer.getViewsCount();
        popularity = decider.decide(viewsCount);
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("area")
    public AreaInnerDto getArea() {
        return area;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("date_create")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    @JsonProperty("views_count")
    public int getViewsCount() {
        return viewsCount;
    }

    @JsonProperty("popularity")
    public String getPopularity() {
        return popularity;
    }
}

package ru.hh.school.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.hh.school.entity.Salary;
import ru.hh.school.entity.Vacancy;
import ru.hh.school.util.PopularityDecider;
import ru.hh.school.util.json.serializers.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class VacancyInnerDto {
    private final Long id;
    private final String name;
    private final AreaInnerDto area;
    private final EmployerInnerDto employer;
    private final LocalDateTime createdAt;
    private final Salary salary;
    private final LocalDateTime dateCreate;
    private final int viewsCount;
    private final String comment;
    private final String popularity;

    public VacancyInnerDto(Vacancy vacancy, PopularityDecider decider) {
        id = vacancy.getId();;
        name = vacancy.getName();
        area = new AreaInnerDto(vacancy.getArea());
        employer = new EmployerInnerDto(vacancy.getEmployer(), decider);
        createdAt = vacancy.getCreatedAt();
        salary = vacancy.getSalary();
        dateCreate = vacancy.getDateCreate();
        viewsCount = vacancy.getViewsCount();
        comment = vacancy.getComment();
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

    @JsonProperty("employer")
    public EmployerInnerDto getEmployer() {
        return employer;
    }

    @JsonProperty("created_at")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("salary")
    public Salary getSalary() {
        return salary;
    }

    @JsonProperty("date_create")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    @JsonProperty("views_count")
    public int getViewsCount() {
        return viewsCount;
    }

    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    @JsonProperty("popularity")
    public String getPopularity(){
        return popularity;
    }
}

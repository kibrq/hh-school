package ru.hh.school.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.hh.school.entity.Salary;
import ru.hh.school.util.json.serializers.LocalDateTimeDeserializer;
import ru.hh.school.util.json.serializers.LocalDateTimeSerializer;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VacancyOuterDtoDetailed extends VacancyOuterDtoShort {
    protected EmployerOuterDtoShort employer;
    protected AreaOuterDto area;
    protected Salary salary;
    protected LocalDateTime createdAt;

    public VacancyOuterDtoDetailed() {}

    public VacancyOuterDtoDetailed(Long id, String name,  EmployerOuterDtoShort employer, AreaOuterDto area, Salary salary, LocalDateTime createdAt) {
        super(id, name);
        this.employer = employer;
        this.area = area;
        this.salary = salary;
        this.createdAt = createdAt;
    }

    @JsonProperty("employer")
    public EmployerOuterDtoShort getEmployer() {
        return employer;
    }

    @JsonProperty("employer")
    public void setEmployer(EmployerOuterDtoShort employer) {
        this.employer = employer;
    }


    @JsonProperty("area")
    public AreaOuterDto getArea() {
        return area;
    }

    @JsonProperty("area")
    public void setArea(AreaOuterDto area) {
        this.area = area;
    }

    @JsonProperty("salary")
    public Salary getSalary() {
        return salary;
    }

    @JsonProperty("salary")
    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    @JsonProperty("created_at")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

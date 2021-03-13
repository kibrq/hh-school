package ru.hh.school.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.hh.school.util.json.serializers.LocalDateTimeDeserializer;
import ru.hh.school.util.json.serializers.LocalDateTimeSerializer;
import ru.hh.school.util.json.views.VacancyViews;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vacancy")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vacancy extends AbstractEntity {

    @Id
    @Column(name = "vacancy_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "area_id")
    private Area area;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "to", column = @Column(name = "salary_to")),
            @AttributeOverride(name = "from", column = @Column(name = "salary_from")),
            @AttributeOverride(name = "currency", column = @Column(name = "salary_currency")),
            @AttributeOverride(name = "gross", column = @Column(name = "salary_gross"))
    })
    private Salary salary;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employer_id")
    private Employer employer;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @Column(name = "views_count")
    private int viewsCount;

    @Column(name = "comment")
    private String comment;

    protected Vacancy() {
    }

    @Override
    @JsonProperty(value = "id")
    @JsonView(VacancyViews.Short.class)
    public Long getId() {
        return id;
    }

    @Override
    @JsonProperty(value = "id")
    @JsonView(VacancyViews.Short.class)
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    protected void refreshImpl(AbstractEntity entity) {
        Vacancy other = (Vacancy) entity;
        setName(other.name);
        setArea(other.area);
        setEmployer(other.employer);
        setCreatedAt(other.createdAt);
        setSalary(other.salary);
    }

    @JsonProperty(value = "name")
    @JsonView(VacancyViews.Short.class)
    public String getName() {
        return name;
    }

    @JsonProperty(value = "name")
    @JsonView(VacancyViews.Short.class)
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(value = "area")
    @JsonView(VacancyViews.Detailed.class)
    public Area getArea() {
        return area;
    }

    @JsonProperty(value = "area")
    @JsonView(VacancyViews.Detailed.class)
    public void setArea(Area area) {
        this.area = area;
    }

    @JsonProperty(value = "salary")
    @JsonView(VacancyViews.Detailed.class)
    public Salary getSalary() {
        return salary;
    }

    @JsonProperty(value = "salary")
    @JsonView(VacancyViews.Detailed.class)
    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    @JsonProperty(value = "created_at")
    @JsonView(VacancyViews.Detailed.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    @JsonProperty(value = "created_at")
    @JsonView(VacancyViews.Detailed.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty(value = "employer")
    @JsonView(VacancyViews.Detailed.class)
    public Employer getEmployer() {
        return employer;
    }

    @JsonProperty(value = "employer")
    @JsonView(VacancyViews.Detailed.class)
    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    @Override
    @JsonProperty(value = "date_create")
    @JsonView(VacancyViews.FavoriteDetailed.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getCreateDate() {
        return dateCreate;
    }

    @Override
    @JsonIgnore
    public void setCreateDate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    @JsonProperty(value = "popularity")
    @JsonView(VacancyViews.FavoriteDetailed.class)
    public String getPopularity() {
        return super.getPopularity();
    }

    @Override
    @JsonProperty(value = "views_count")
    @JsonView(VacancyViews.FavoriteDetailed.class)
    public int getViewsCount() {
        return viewsCount;
    }

    @Override
    @JsonIgnore
    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    @Override
    @JsonProperty(value = "comment")
    @JsonView(VacancyViews.FavoriteDetailed.class)
    public String getComment() {
        return comment;
    }

    @Override
    @JsonIgnore
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public void seen() {
        super.seen();
        employer.seen();
    }
}

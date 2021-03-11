package ru.hh.school.entity;

import com.fasterxml.jackson.annotation.*;
import ru.hh.school.util.EmployerViews;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "employer")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employer {

    private static final int MAX_DESCRIPTION_LENGTH = 256;

    @Id
    @Column(name = "employer_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_create")
    private LocalDateTime createDate;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "area_id")
    private Area area;

    @Column(name = "comment")
    private String comment;

    @Column(name = "views_count")
    private Integer viewsCount = 0;

    protected Employer() {
    }

    @JsonProperty
    @JsonView(EmployerViews.Short.class)
    public Long getId() {
        return id;
    }

    @JsonProperty
    @JsonView(EmployerViews.Short.class)
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty
    @JsonView(EmployerViews.Short.class)
    public String getName() {
        return name;
    }

    @JsonProperty
    @JsonView(EmployerViews.Short.class)
    public void setName(String name) {
        this.name = name;
    }

    @JsonView(EmployerViews.FavoriteDetailed.class)
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    @JsonIgnore
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @JsonProperty
    @JsonView(EmployerViews.Detailed.class)
    public String getDescription() {
        return description;
    }

    @JsonProperty
    @JsonView(EmployerViews.Detailed.class)
    public void setDescription(String description) {
        this.description = description.substring(0, MAX_DESCRIPTION_LENGTH);
    }

    @JsonProperty
    @JsonView(EmployerViews.Detailed.class)
    public Area getArea() {
        return area;
    }

    @JsonProperty
    @JsonView(EmployerViews.Detailed.class)
    public void setArea(Area area) {
        this.area = area;
    }

    @JsonProperty
    @JsonView(EmployerViews.FavoriteDetailed.class)
    public String getComment() {
        return comment;
    }

    @JsonProperty
    @JsonView(EmployerViews.FavoriteDetailed.class)
    public void setComment(String comment) {
        this.comment = comment;
    }

    @JsonProperty
    @JsonView(EmployerViews.FavoriteDetailed.class)
    public String getPopularity() {
        return Popularity.getByViews(viewsCount).name();
    }

    @JsonProperty
    @JsonView(EmployerViews.FavoriteDetailed.class)
    public Integer getViewsCount() {
        return viewsCount;
    }

    @JsonProperty
    @JsonView(EmployerViews.FavoriteDetailed.class)
    public void setViewsCount(Integer viewsCount) {
        this.viewsCount = viewsCount;
    }

    public void refresh(String comment) {
        this.createDate = LocalDateTime.now();
        this.comment = comment;
    }

    public void seen() {
        this.viewsCount += 1;
    }

}

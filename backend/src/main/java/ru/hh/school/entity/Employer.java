package ru.hh.school.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.hh.school.util.json.serializers.LocalDateTimeDeserializer;
import ru.hh.school.util.json.serializers.LocalDateTimeSerializer;
import ru.hh.school.util.json.views.EmployerViews;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "employer")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employer extends AbstractEntity {

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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "area_id")
    private Area area;

    @Column(name = "comment")
    private String comment;

    @Column(name = "views_count")
    private int viewsCount = 0;

    protected Employer() {
    }

    @Override
    @JsonProperty
    @JsonView(EmployerViews.Short.class)
    public Long getId() {
        return id;
    }

    @Override
    @JsonProperty
    @JsonView(EmployerViews.Short.class)
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    protected void refreshImpl(AbstractEntity entity) {
        Employer other = (Employer) entity;
        setName(other.name);
        setDescription(other.description);
        setArea(other.area);
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

    @Override
    @JsonProperty(value = "date_create")
    @JsonView(EmployerViews.FavoriteDetailed.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    @Override
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

    @Override
    @JsonProperty
    @JsonView(EmployerViews.FavoriteDetailed.class)
    public String getComment() {
        return comment;
    }

    @Override
    @JsonProperty
    @JsonView(EmployerViews.FavoriteDetailed.class)
    public void setComment(String comment) {
        this.comment = comment;
    }

    @JsonProperty
    @JsonView(EmployerViews.FavoriteDetailed.class)
    public String getPopularity() {
        return super.getPopularity();
    }

    @Override
    @JsonProperty(value = "views_count")
    @JsonView(EmployerViews.FavoriteDetailed.class)
    public int getViewsCount() {
        return viewsCount;
    }

    @Override
    @JsonProperty
    @JsonView(EmployerViews.FavoriteDetailed.class)
    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public void refresh(String comment) {
        this.createDate = LocalDateTime.now();
        this.comment = comment;
    }

}

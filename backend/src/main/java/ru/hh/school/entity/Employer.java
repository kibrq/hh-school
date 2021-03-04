package ru.hh.school.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "employer")
public class Employer {
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

    @Column(name = "popularity")
    private Integer popularity;

    @Column(name = "views_count")
    private Integer viewsCount;

    public Employer() {

    }

    public Employer(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

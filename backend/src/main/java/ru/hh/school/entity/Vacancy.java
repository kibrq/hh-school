package ru.hh.school.entity;

import ru.hh.school.dto.VacancyOuterDtoDetailed;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vacancy")
public class Vacancy extends AbstractEntity {

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


    protected Vacancy() {
    }

    public Vacancy(VacancyOuterDtoDetailed dto, Area area, Employer employer, String comment) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.area = area;
        this.employer = employer;
        this.salary = dto.getSalary();
        this.createdAt = dto.getCreatedAt();
        this.post(comment);
    }

    public void refresh(VacancyOuterDtoDetailed dto) {
        this.name = dto.getName();
        this.salary = dto.getSalary();
    }

    @Override
    public void seen() {
        super.seen();
        employer.seen();
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

}

package ru.hh.school.service;

import ru.hh.school.dao.GenericDao;
import ru.hh.school.dto.EmployerOuterDtoDetailed;
import ru.hh.school.dto.VacancyInnerDto;
import ru.hh.school.dto.VacancyOuterDtoDetailed;
import ru.hh.school.entity.Area;
import ru.hh.school.entity.Employer;
import ru.hh.school.entity.Vacancy;
import ru.hh.school.util.Pagination;
import ru.hh.school.util.PopularityDecider;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

public class FavoritesVacancyService {
    private final FavoritesEmployerService employerService;
    private final PopularityDecider decider;
    private final GenericDao dao;

    public FavoritesVacancyService(FavoritesEmployerService employerService, PopularityDecider decider, GenericDao dao) {
        this.employerService = employerService;
        this.decider = decider;
        this.dao = dao;
    }

    @Transactional
    public Vacancy postVacancy(VacancyOuterDtoDetailed vacancyDto, EmployerOuterDtoDetailed employerDto, String comment) {
        Employer employer = employerService.postEmployer(employerDto, comment);
        Area area = dao.postEntity(vacancyDto.getArea().getId(), () -> new Area(vacancyDto.getArea()), Area.class);
        return dao.postEntity(vacancyDto.getId(), () -> new Vacancy(vacancyDto, area, employer, comment), Vacancy.class);
    }

    @Transactional
    public List<VacancyInnerDto> getVacancies(Pagination pagination) {
        return dao.getAll(Vacancy.class)
                .skip((long) pagination.page * pagination.perPage)
                .limit(pagination.perPage)
                .peek(Vacancy::seen)
                .map(v -> new VacancyInnerDto(v, decider))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateComment(Long id, String comment) {
        Vacancy vacancy = dao.getEntityThrowing(id, Vacancy.class);
        vacancy.setComment(comment);
    }

    @Transactional
    public void delete(Long id) {
        dao.delete(id, Vacancy.class);
    }

    @Transactional
    public void refresh(VacancyOuterDtoDetailed vacancyDto) {
        Vacancy vacancy = dao.getEntityThrowing(vacancyDto.getId(), Vacancy.class);
        vacancy.refresh(vacancyDto);
    }
}

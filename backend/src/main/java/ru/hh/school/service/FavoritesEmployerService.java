package ru.hh.school.service;

import ru.hh.school.dao.GenericDao;
import ru.hh.school.dto.EmployerInnerDto;
import ru.hh.school.dto.EmployerOuterDtoDetailed;
import ru.hh.school.entity.AbstractEntity;
import ru.hh.school.entity.Area;
import ru.hh.school.entity.Employer;
import ru.hh.school.util.Pagination;
import ru.hh.school.util.PopularityDecider;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

public class FavoritesEmployerService {

    private final GenericDao dao;
    private final PopularityDecider decider;

    public FavoritesEmployerService(GenericDao dao, PopularityDecider decider) {
        this.dao = dao;
        this.decider = decider;
    }

    @Transactional
    public Employer postEmployer(EmployerOuterDtoDetailed dto, String comment) {
        Area area = dao.postEntity(dto.getArea().getId(), () -> new Area(dto.getArea()), Area.class);
        return dao.postEntity(dto.getId(), () -> new Employer(dto, area, comment), Employer.class);
    }

    @Transactional
    public List<EmployerInnerDto> getEmployers(Pagination pagination) {
        return dao.getAll(Employer.class)
                .skip((long) pagination.page * pagination.perPage)
                .limit(pagination.perPage)
                .peek(AbstractEntity::seen)
                .map((e) -> new EmployerInnerDto(e, decider))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateComment(Long id, String comment) {
        Employer employer = dao.getEntityThrowing(id, Employer.class);
        employer.setComment(comment);
    }

    @Transactional
    public void delete(Long id) {
        dao.delete(id, Employer.class);
    }

    @Transactional
    public void refresh(EmployerOuterDtoDetailed dto) {
        Employer employer = dao.getEntityThrowing(dto.getId(), Employer.class);
        employer.refresh(dto);
    }


}

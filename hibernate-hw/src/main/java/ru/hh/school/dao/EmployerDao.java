package ru.hh.school.dao;

import org.hibernate.SessionFactory;
import ru.hh.school.entity.Employer;
import ru.hh.school.entity.Vacancy;

import java.util.List;

public class EmployerDao extends GenericDao {

  public EmployerDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * TODO: здесь нужен метод, позволяющий сразу загрузить вакасии, связанные с работодателем и в некоторых случаях
   * избежать org.hibernate.LazyInitializationException
   * Также в запрос должен передаваться параметр employerId
   * <p>
   * https://vladmihalcea.com/the-best-way-to-handle-the-lazyinitializationexception/
   */

  public Employer getEager(int employerId) {
    return getSession().createQuery("select distinct e " +
            "from Employer e " +
            "join fetch e.vacancies " +
            "where e.id = :employerId", Employer.class)
            .setParameter("employerId", employerId).getSingleResult();
  }

}

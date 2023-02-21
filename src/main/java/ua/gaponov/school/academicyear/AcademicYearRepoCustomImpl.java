package ua.gaponov.school.academicyear;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class AcademicYearRepoCustomImpl implements AcademicYearRepoCustom {

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public List<AcademicYear> findAllByKeywords(String keywords) {
    List<AcademicYear> result;
    Query q = entityManager.createQuery(
        "SELECT a FROM AcademicYear a WHERE lower(a.name) LIKE lower(?1)",
        AcademicYear.class);
    q.setParameter(1, "%" + keywords + "%");
    result = q.getResultList();
    return result;
  }
}

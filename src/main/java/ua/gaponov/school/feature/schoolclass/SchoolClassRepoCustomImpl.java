package ua.gaponov.school.feature.schoolclass;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class SchoolClassRepoCustomImpl implements SchoolClassRepoCustom {

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public List<SchoolClass> findAllByKeywords(String keywords) {
    List<SchoolClass> result;
    Query q = entityManager.createQuery(
        "SELECT s FROM SchoolClass s WHERE lower(s.name) LIKE lower(?1)",
        SchoolClass.class);
    q.setParameter(1, "%" + keywords + "%");
    result = q.getResultList();
    return result;
  }
}

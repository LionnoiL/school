package ua.gaponov.school.feature.school;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class SchoolRepoCustomImpl implements SchoolRepoCustom {

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public List<School> findAllByKeywords(String keywords) {
    List<School> result;
    Query q = entityManager.createQuery(
        "SELECT s FROM School s WHERE lower(s.name) LIKE lower(?1)",
        School.class);
    q.setParameter(1, "%" + keywords + "%");
    result = q.getResultList();
    return result;
  }
}

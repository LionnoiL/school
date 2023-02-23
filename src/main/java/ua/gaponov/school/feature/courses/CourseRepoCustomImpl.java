package ua.gaponov.school.feature.courses;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class CourseRepoCustomImpl implements CourseRepoCustom {

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public List<Course> findAllByKeywords(String keywords) {
    List<Course> result;
    Query q = entityManager.createQuery(
        "SELECT s FROM Course s WHERE s.active and lower(s.name) LIKE lower(?1)",
        Course.class);
    q.setParameter(1, "%" + keywords + "%");
    result = q.getResultList();
    return result;
  }
}

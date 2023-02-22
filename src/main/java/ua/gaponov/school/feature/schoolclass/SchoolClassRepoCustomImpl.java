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

  @Override
  public List<SchoolClass> findAllBySchoolId(int id) {
    List<SchoolClass> result;
    Query q = entityManager.createQuery(
        "SELECT s FROM SchoolClass s WHERE s.school.id =?1",
        SchoolClass.class);
    q.setParameter(1, id);
    result = q.getResultList();
    return result;
  }

  @Override
  public List<SchoolClass> findAllBySchoolIdAndKeywords(int id, String keywords) {
    List<SchoolClass> result;
    Query q = entityManager.createQuery(
        "SELECT s FROM SchoolClass s WHERE s.school.id =?1 and lower(s.name) LIKE lower(?2)",
        SchoolClass.class);
    q.setParameter(1, id);
    q.setParameter(2, "%" + keywords + "%");
    result = q.getResultList();
    return result;
  }
}

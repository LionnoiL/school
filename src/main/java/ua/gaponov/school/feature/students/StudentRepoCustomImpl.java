package ua.gaponov.school.feature.students;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class StudentRepoCustomImpl implements StudentRepoCustom {

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public List<Student> findAllByKeywords(String keywords) {
    List<Student> result;
    Query q = entityManager.createQuery(
        "SELECT s FROM Student s WHERE lower(s.firstName) LIKE lower(?1) or lower(s.lastName) LIKE lower(?1)",
        Student.class);
    q.setParameter(1, "%" + keywords + "%");
    result = q.getResultList();
    return result;
  }
}

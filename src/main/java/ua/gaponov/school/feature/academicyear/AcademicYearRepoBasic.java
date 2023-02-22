package ua.gaponov.school.feature.academicyear;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AcademicYearRepoBasic extends JpaRepository<AcademicYear, Integer> {
  @Query(nativeQuery = true, value = "SELECT * FROM academic_year y WHERE y.school_id = :id")
  List<AcademicYear> findBySchoolId(int id);
}

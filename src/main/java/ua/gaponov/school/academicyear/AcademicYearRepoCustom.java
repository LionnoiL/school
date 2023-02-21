package ua.gaponov.school.academicyear;

import java.util.List;

public interface AcademicYearRepoCustom {

  List<AcademicYear> findAllByKeywords(String keywords);
}

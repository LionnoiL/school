package ua.gaponov.school.feature.academicyear;

import java.util.List;

public interface AcademicYearRepoCustom {

  List<AcademicYear> findAllByKeywords(String keywords);
}

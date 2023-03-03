package ua.gaponov.school.feature.schoolclass;

import java.util.List;

public interface SchoolClassRepoCustom {

  List<SchoolClass> findAllByKeywords(String keywords);
  List<SchoolClass> findAllBySchoolId(long id);
  List<SchoolClass> findAllBySchoolIdAndKeywords(long id, String keywords);
}

package ua.gaponov.school.feature.schoolclass;

import java.util.List;

public interface SchoolClassRepoCustom {

  List<SchoolClass> findAllByKeywords(String keywords);
  List<SchoolClass> findAllBySchoolId(int id);
  List<SchoolClass> findAllBySchoolIdAndKeywords(int id, String keywords);
}

package ua.gaponov.school.feature.schoolclass;

import java.util.List;

public interface SchoolClassRepoCustom {

  List<SchoolClass> findAllByKeywords(String keywords);
}

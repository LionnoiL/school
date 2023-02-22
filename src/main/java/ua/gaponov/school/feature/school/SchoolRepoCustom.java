package ua.gaponov.school.feature.school;

import java.util.List;

public interface SchoolRepoCustom {

  List<School> findAllByKeywords(String keywords);
}

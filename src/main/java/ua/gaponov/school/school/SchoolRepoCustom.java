package ua.gaponov.school.school;

import java.util.List;

public interface SchoolRepoCustom {

  List<School> findAllByKeywords(String keywords);
}

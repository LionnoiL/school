package ua.gaponov.school.feature.courses;

import java.util.List;

public interface CourseRepoCustom {

  List<Course> findAllByKeywords(String keywords);
}

package ua.gaponov.school.feature.students;

import java.util.List;

public interface StudentRepoCustom {

  List<Student> findAllByKeywords(String keywords);
}

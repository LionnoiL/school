package ua.gaponov.school.feature.students;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepoBasic extends JpaRepository<Student, Long> {

}

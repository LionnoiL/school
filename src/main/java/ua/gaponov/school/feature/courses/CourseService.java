package ua.gaponov.school.feature.courses;

import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CourseService {

  private final CourseRepo courseRepo;

  public List<Course> getAll() {
    return courseRepo.findAll();
  }

  public Course findById(int id) throws NotFoundException {
    Optional<Course> optional = courseRepo.findById(id);
    if (optional.isEmpty()) {
      throw new NotFoundException("Course not present");
    }

    return optional.get();
  }

  public void save(Course course) {
    courseRepo.save(course);
  }

  public void delete(Course course) {
    courseRepo.delete(course);
  }
}

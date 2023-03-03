package ua.gaponov.school.feature.students;

import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudentService {

  private final StudentRepo studentRepo;

  public List<Student> getAll() {
    return studentRepo.findAll();
  }

  public List<Student> getAll(String keywords) {
    if (keywords != null && !keywords.isEmpty()) {
      return studentRepo.findAllByKeywords(keywords);
    } else {
      return studentRepo.findAll();
    }
  }

  public Student findById(long id) throws NotFoundException {
    Optional<Student> optional = studentRepo.findById(id);
    if (optional.isEmpty()) {
      throw new NotFoundException("Student not present");
    }

    return optional.get();
  }

  public void save(Student student) {
    studentRepo.save(student);
  }

  public void delete(Student student) {
    studentRepo.delete(student);
  }
}

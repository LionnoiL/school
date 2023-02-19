package ua.gaponov.school.grades;

import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GradeService {

  private final GradeRepo gradeRepo;

  public List<Grade> getAll() {
    return gradeRepo.findAll();
  }

  public Grade findById(int id) throws NotFoundException {
    Optional<Grade> optional = gradeRepo.findById(id);
    if (optional.isEmpty()) {
      throw new NotFoundException("Grades not present");
    }

    return optional.get();
  }

  public void save(Grade grade) {
    gradeRepo.save(grade);
  }

  public void delete(Grade grade) {
    gradeRepo.delete(grade);
  }
}

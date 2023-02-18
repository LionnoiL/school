package ua.gaponov.school.grades;

import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GradesService {

  private final GradesRepo gradesRepo;

  public List<Grades> getAll() {
    return gradesRepo.findAll();
  }

  public Grades findById(int id) throws NotFoundException {
    Optional<Grades> optional = gradesRepo.findById(id);
    if (optional.isEmpty()) {
      throw new NotFoundException("Grades not present");
    }

    return optional.get();
  }

  public void save(Grades grades) {
    gradesRepo.save(grades);
  }

  public void delete(Grades grades) {
    gradesRepo.delete(grades);
  }
}

package ua.gaponov.school.academicyear;

import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AcademicYearService {

  private final AcademicYearRepo academicYearRepo;

  public List<AcademicYear> getAll() {
    return academicYearRepo.findAll();
  }

  public AcademicYear findById(int id) throws NotFoundException {
    Optional<AcademicYear> optional = academicYearRepo.findById(id);
    if (optional.isEmpty()) {
      throw new NotFoundException("Academic year not present");
    }

    return optional.get();
  }

  public void save(AcademicYear year) {
    academicYearRepo.save(year);
  }

  public void delete(AcademicYear year) {
    academicYearRepo.delete(year);
  }
}

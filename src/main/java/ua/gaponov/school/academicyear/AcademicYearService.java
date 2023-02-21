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

  public List<AcademicYear> getAll(String keywords) {
    if (keywords != null && !keywords.isEmpty()) {
      return academicYearRepo.findAllByKeywords(keywords);
    } else {
      return academicYearRepo.findAll();
    }
  }

  public List<AcademicYear> getAllBySchool(int id) {
    return academicYearRepo.findBySchoolId(id);
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

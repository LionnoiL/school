package ua.gaponov.school.feature.school;

import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SchoolService {

  private final SchoolRepo schoolRepo;

  public List<School> getAll() {
    return schoolRepo.findAll();
  }

  public List<School> getAll(String keywords) {
    if (keywords != null && !keywords.isEmpty()) {
      return schoolRepo.findAllByKeywords(keywords);
    } else {
      return schoolRepo.findAll();
    }
  }

  public School findById(int id) throws NotFoundException {
    Optional<School> optional = schoolRepo.findById(id);
    if (optional.isEmpty()) {
      throw new NotFoundException("School not present");
    }

    return optional.get();
  }

  public void save(School school) {
    schoolRepo.save(school);
  }

  public void delete(School school) {
    schoolRepo.delete(school);
  }
}

package ua.gaponov.school.feature.schoolclass;

import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SchoolClassService {

  private final SchoolClassRepo schoolClassRepo;

  public List<SchoolClass> getAll() {
    return schoolClassRepo.findAll();
  }

  public List<SchoolClass> getAll(String keywords) {
    if (keywords != null && !keywords.isEmpty()) {
      return schoolClassRepo.findAllByKeywords(keywords);
    } else {
      return schoolClassRepo.findAll();
    }
  }

  public List<SchoolClass> getAllBySchoolId(String keywords, long school_id)  {
    if (keywords != null && !keywords.isEmpty()) {
      return schoolClassRepo.findAllBySchoolIdAndKeywords(school_id, keywords);
    } else {
      return schoolClassRepo.findAllBySchoolId(school_id);
    }
  }



  public SchoolClass findById(long id) throws NotFoundException {
    Optional<SchoolClass> optional = schoolClassRepo.findById(id);
    if (optional.isEmpty()) {
      throw new NotFoundException("School class not present");
    }

    return optional.get();
  }

  public void save(SchoolClass schoolClass) {
    schoolClassRepo.save(schoolClass);
  }

  public void delete(SchoolClass schoolClass) {
    schoolClassRepo.delete(schoolClass);
  }
}

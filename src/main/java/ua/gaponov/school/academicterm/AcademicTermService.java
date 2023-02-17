package ua.gaponov.school.academicterm;

import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AcademicTermService {

  private final AcademicTermRepo academicTermRepo;

  public List<AcademicTerm> getAll() {
    return academicTermRepo.findAll();
  }

  public AcademicTerm findById(int id) throws NotFoundException {
    Optional<AcademicTerm> optional = academicTermRepo.findById(id);
    if (optional.isEmpty()) {
      throw new NotFoundException("Academic term not present");
    }

    return optional.get();
  }

  public void save(AcademicTerm term) {
    academicTermRepo.save(term);
  }

  public void delete(AcademicTerm term) {
    academicTermRepo.delete(term);
  }
}

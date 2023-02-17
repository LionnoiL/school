package ua.gaponov.school.academicterm;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AcademicTermService {

  private final AcademicTermRepo academicTermRepo;

  public List<AcademicTermDto> getAll() {
    List<AcademicTerm> terms = academicTermRepo.findAll();
    return terms.stream()
        .map(AcademicTerm::toDto)
        .sorted(Comparator.comparing(termDto -> (Integer) termDto.getId()))
        .collect(Collectors.toList());
  }

  public AcademicTermDto findById(int id) throws NotFoundException {
    Optional<AcademicTerm> optional = academicTermRepo.findById(id);
    if (optional.isEmpty()) {
      throw new NotFoundException("Academic term not present");
    }
    AcademicTerm term = optional.get();

    return AcademicTerm.toDto(term);
  }

  public void save(AcademicTermDto termDto) {
    AcademicTerm term = AcademicTerm.fromDto(termDto);
    academicTermRepo.save(term);
  }

  public void delete(AcademicTermDto termDto) {
    AcademicTerm term = AcademicTerm.fromDto(termDto);
    academicTermRepo.delete(term);
  }
}

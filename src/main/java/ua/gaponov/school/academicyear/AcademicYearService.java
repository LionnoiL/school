package ua.gaponov.school.academicyear;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AcademicYearService {

  private final AcademicYearRepo academicYearRepo;

  public List<AcademicYearDto> getAll() {
    List<AcademicYear> years = academicYearRepo.findAll();
    return years.stream()
        .map(AcademicYear::toDto)
        .sorted(Comparator.comparing(yearDto -> (Integer) yearDto.getId()))
        .collect(Collectors.toList());
  }

  public AcademicYearDto findById(int id) throws NotFoundException {
    Optional<AcademicYear> optional = academicYearRepo.findById(id);
    if (optional.isEmpty()) {
      throw new NotFoundException("Academic year not present");
    }
    AcademicYear year = optional.get();

    return AcademicYear.toDto(year);
  }

  public void save(AcademicYearDto yearDto) {
    AcademicYear year = AcademicYear.fromDto(yearDto);
    academicYearRepo.save(year);
  }

  public void delete(AcademicYearDto yearDto) {
    AcademicYear year = AcademicYear.fromDto(yearDto);
    academicYearRepo.delete(year);
  }
}

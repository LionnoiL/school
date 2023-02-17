package ua.gaponov.school.school;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SchoolService {

  private final SchoolRepo schoolRepo;

  public List<SchoolDto> getAll() {
    List<School> schools = schoolRepo.findAll();
    return schools.stream()
        .map(School::toDto)
        .sorted(Comparator.comparing(SchoolDto::getName))
        .collect(Collectors.toList());
  }

  public SchoolDto findById(int id) throws NotFoundException {
    Optional<School> optional = schoolRepo.findById(id);
    if (optional.isEmpty()) {
      throw new NotFoundException("School not present");
    }
    School school = optional.get();

    return School.toDto(school);
  }

  public void save(SchoolDto schoolDto) {
    School school = School.fromDto(schoolDto);
    schoolRepo.save(school);
  }

  public void delete(SchoolDto schoolDto) {
    School school = School.fromDto(schoolDto);
    schoolRepo.delete(school);
  }
}

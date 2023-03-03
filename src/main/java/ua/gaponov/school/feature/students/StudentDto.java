package ua.gaponov.school.feature.students;

import lombok.Builder;
import lombok.Data;
import ua.gaponov.school.feature.schoolclass.SchoolClassDto;

@Data
@Builder
public class StudentDto {

  private long id;
  private String firstName;
  private String lastName;
  private SchoolClassDto schoolClass;
}

package ua.gaponov.school.schoolclass;

import lombok.Builder;
import lombok.Data;
import ua.gaponov.school.school.SchoolDto;

@Data
@Builder
public class SchoolClassDto {

  private long id;
  private String name;
  private String description;
  private SchoolDto school;
}

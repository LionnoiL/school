package ua.gaponov.school.feature.schoolclass;

import lombok.Builder;
import lombok.Data;
import ua.gaponov.school.feature.school.SchoolDto;

@Data
@Builder
public class SchoolClassDto {

  private long id;
  private String name;
  private String description;
  private SchoolDto school;
}

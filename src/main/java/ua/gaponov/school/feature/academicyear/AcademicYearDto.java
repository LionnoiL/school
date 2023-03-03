package ua.gaponov.school.feature.academicyear;

import lombok.Builder;
import lombok.Data;
import ua.gaponov.school.feature.school.SchoolDto;

@Data
@Builder
public class AcademicYearDto {

  private long id;
  private String name;
  private String startDate;
  private String endDate;
  private SchoolDto school;
}

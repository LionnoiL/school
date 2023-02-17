package ua.gaponov.school.academicyear;

import lombok.Builder;
import lombok.Data;
import ua.gaponov.school.school.SchoolDto;

@Data
@Builder
public class AcademicYearDto {

  private int id;
  private String name;
  private String startDate;
  private String endDate;
  private SchoolDto school;
}

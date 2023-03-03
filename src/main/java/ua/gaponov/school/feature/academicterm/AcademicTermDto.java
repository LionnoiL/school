package ua.gaponov.school.feature.academicterm;

import lombok.Builder;
import lombok.Data;
import ua.gaponov.school.feature.academicyear.AcademicYearDto;

@Data
@Builder
public class AcademicTermDto {

  private long id;
  private String name;
  private String startDate;
  private String endDate;
  private AcademicYearDto academicYear;
}

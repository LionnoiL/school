package ua.gaponov.school.academicterm;

import lombok.Builder;
import lombok.Data;
import ua.gaponov.school.academicyear.AcademicYearDto;

@Data
@Builder
public class AcademicTermDto {

  private int id;
  private String name;
  private String startDate;
  private String endDate;
  private AcademicYearDto academicYear;
}

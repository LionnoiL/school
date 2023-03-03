package ua.gaponov.school.feature.grades;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GradeDto {

  private long id;
  private String name;
  private double rate;
}

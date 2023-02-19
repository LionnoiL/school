package ua.gaponov.school.grades;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GradeDto {

  private int id;
  private String name;
  private double rate;
}

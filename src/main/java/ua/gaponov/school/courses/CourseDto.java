package ua.gaponov.school.courses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseDto {

  private int id;
  private String name;
  private boolean active;
  private String description;
}

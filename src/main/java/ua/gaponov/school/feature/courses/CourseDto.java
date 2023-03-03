package ua.gaponov.school.feature.courses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseDto {

  private long id;
  private String name;
  private boolean active;
  private String description;
}

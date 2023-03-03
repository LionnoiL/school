package ua.gaponov.school.feature.courses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import ua.gaponov.school.model.NamedEntity;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "courses")
public class Course extends NamedEntity {

  @Column(name = "active")
  private boolean active;

  @Column(name = "description")
  private String description;

  public static CourseDto toDto(Course course) {
    return CourseDto.builder()
        .id(course.getId())
        .name(course.getName())
        .description(course.getDescription())
        .active(course.isActive())
        .build();
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}

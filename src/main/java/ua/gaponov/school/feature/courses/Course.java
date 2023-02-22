package ua.gaponov.school.feature.courses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "courses")
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "active")
  private boolean active;

  @Column(name = "description")
  private String description;

  public Course() {
  }

  public Course(int id, String name, boolean active, String description) {
    this.id = id;
    this.name = name;
    this.active = active;
    this.description = description;
  }

  public static CourseDto toDto(Course course) {
    return CourseDto.builder()
        .id(course.getId())
        .name(course.getName())
        .description(course.getDescription())
        .active(course.isActive())
        .build();
  }

  public static Course fromDto(CourseDto courseDto) {
    return Course.builder()
        .id(courseDto.getId())
        .name(courseDto.getName())
        .description(courseDto.getDescription())
        .active(courseDto.isActive())
        .build();
  }
}

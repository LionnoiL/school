package ua.gaponov.school.feature.schoolclass;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.gaponov.school.feature.courses.Course;
import ua.gaponov.school.feature.school.School;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClass {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(name = "number_class")
  private String name;
  @Column(name = "description")
  private String description;
  @ManyToOne
  @JoinColumn(name = "school_id")
  private School school;

  @JoinTable(
      name = "class_courses",
      joinColumns = @JoinColumn(name = "class_id"),
      inverseJoinColumns = @JoinColumn(name = "courses_id")
  )
  @ManyToMany
  private Set<Course> courses;

  public static SchoolClassDto toDto(SchoolClass schoolClass) {
    return SchoolClassDto.builder()
        .id(schoolClass.getId())
        .name(schoolClass.getName())
        .description(schoolClass.getDescription())
        .school(School.toDto(schoolClass.getSchool()))
        .courses(schoolClass.getCourses()
            .stream()
            .map(Course::toDto)
            .collect(Collectors.toSet()))
        .build();
  }

  public static SchoolClass fromDto(SchoolClassDto schoolClassDto) {
    return SchoolClass.builder()
        .id(schoolClassDto.getId())
        .name(schoolClassDto.getName())
        .description(schoolClassDto.getDescription())
        .school(School.fromDto(schoolClassDto.getSchool()))
        .courses(schoolClassDto.getCourses()
            .stream()
            .map(Course::fromDto)
            .collect(Collectors.toSet()))
        .build();
  }
}

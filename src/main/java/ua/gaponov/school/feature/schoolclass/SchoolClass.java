package ua.gaponov.school.feature.schoolclass;

import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import ua.gaponov.school.feature.courses.Course;
import ua.gaponov.school.feature.school.School;
import ua.gaponov.school.model.NamedEntity;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClass extends NamedEntity {

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public School getSchool() {
    return school;
  }

  public void setSchool(School school) {
    this.school = school;
  }

  public Set<Course> getCourses() {
    return courses;
  }

  public void setCourses(Set<Course> courses) {
    this.courses = courses;
  }
}

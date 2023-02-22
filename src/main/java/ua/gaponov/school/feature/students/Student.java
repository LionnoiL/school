package ua.gaponov.school.feature.students;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import ua.gaponov.school.feature.schoolclass.SchoolClass;

@Entity
@Data
@Builder
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "first_name", nullable = false)
  private String firstName;
  @Column(name = "last_name", nullable = false)
  private String lastName;
  @ManyToOne
  @JoinColumn(name = "school_class_id")
  private SchoolClass schoolClass;

  public Student(int id, String firstName, String lastName, SchoolClass schoolClass) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.schoolClass = schoolClass;
  }

  public Student() {
  }

  public static StudentDto toDto(Student student) {
    return StudentDto.builder()
        .id(student.getId())
        .firstName(student.getFirstName())
        .lastName(student.getLastName())
        .schoolClass(SchoolClass.toDto(student.getSchoolClass()))
        .build();
  }

  public static Student fromDto(StudentDto studentDto) {
    return Student.builder()
        .id(studentDto.getId())
        .firstName(studentDto.getFirstName())
        .lastName(studentDto.getLastName())
        .schoolClass(SchoolClass.fromDto(studentDto.getSchoolClass()))
        .build();
  }
}

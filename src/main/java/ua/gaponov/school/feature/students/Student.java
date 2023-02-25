package ua.gaponov.school.feature.students;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.gaponov.school.feature.schoolclass.SchoolClass;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
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

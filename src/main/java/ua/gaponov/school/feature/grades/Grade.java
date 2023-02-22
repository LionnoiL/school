package ua.gaponov.school.feature.grades;

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
@Table(name = "grades")
public class Grade {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "rate")
  private double rate;

  public Grade() {
  }

  public Grade(int id, String name, double rate) {
    this.id = id;
    this.name = name;
    this.rate = rate;
  }

  public static GradeDto toDto(Grade grade){
    return GradeDto.builder()
        .id(grade.getId())
        .name(grade.getName())
        .rate(grade.getRate())
        .build();
  }

  public static Grade fromDto(GradeDto gradeDto){
    return Grade.builder()
        .id(gradeDto.getId())
        .name(gradeDto.getName())
        .rate(gradeDto.getRate())
        .build();
  }
}

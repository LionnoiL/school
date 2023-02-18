package ua.gaponov.school.grades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Grades {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "rate")
  private double rate;

  public Grades() {
  }

  public Grades(int id, String name, double rate) {
    this.id = id;
    this.name = name;
    this.rate = rate;
  }

  public static GradesDto toDto(Grades grades){
    return GradesDto.builder()
        .id(grades.getId())
        .name(grades.getName())
        .rate(grades.getRate())
        .build();
  }

  public static Grades fromDto(GradesDto gradesDto){
    return Grades.builder()
        .id(gradesDto.getId())
        .name(gradesDto.getName())
        .rate(gradesDto.getRate())
        .build();
  }
}

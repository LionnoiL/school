package ua.gaponov.school.feature.grades;

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
@Table(name = "grades")
public class Grade extends NamedEntity {

  @Column(name = "rate")
  private double rate;

  public static GradeDto toDto(Grade grade) {
    return GradeDto.builder()
        .id(grade.getId())
        .name(grade.getName())
        .rate(grade.getRate())
        .build();
  }

  public double getRate() {
    return rate;
  }

  public void setRate(double rate) {
    this.rate = rate;
  }
}

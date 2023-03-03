package ua.gaponov.school.feature.academicyear;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import ua.gaponov.school.feature.school.School;
import ua.gaponov.school.model.NamedEntity;
import ua.gaponov.school.utils.DateUtils;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcademicYear extends NamedEntity {

  @Column(name = "start_year", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_year", nullable = false)
  private LocalDate endDate;

  @ManyToOne
  @JoinColumn(name = "school_id")
  private School school;

  public static AcademicYearDto toDto(AcademicYear year) {
    return AcademicYearDto.builder()
        .id(year.getId())
        .name(year.getName())
        .startDate(DateUtils.getStringFromLocalDate(year.getStartDate()))
        .endDate(DateUtils.getStringFromLocalDate(year.getEndDate()))
        .school(School.toDto(year.getSchool()))
        .build();
  }

  public static AcademicYear fromDto(AcademicYearDto yearDto) {
    AcademicYear year = new AcademicYear();
    year.setId(yearDto.getId());
    year.setName(yearDto.getName());
    year.setStartDate(DateUtils.getLocalDateFromString(yearDto.getStartDate()));
    year.setEndDate(DateUtils.getLocalDateFromString(yearDto.getEndDate()));
    year.setSchool(School.fromDto(yearDto.getSchool()));
    return year;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public School getSchool() {
    return school;
  }

  public void setSchool(School school) {
    this.school = school;
  }
}

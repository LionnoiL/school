package ua.gaponov.school.academicyear;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import ua.gaponov.school.school.School;
import ua.gaponov.school.utils.DateUtils;

@Entity
@Data
@Builder
public class AcademicYear {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "academic_year_name", nullable = false)
  private String name;

  @Column(name = "start_year", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_year", nullable = false)
  private LocalDate endDate;

  @ManyToOne
  @JoinColumn(name = "school_id")
  private School school;

  public AcademicYear() {

  }

  public AcademicYear(int id, String name, LocalDate startDate, LocalDate endDate, School school) {
    this.id = id;
    this.name = name;
    this.startDate = startDate;
    this.endDate = endDate;
    this.school = school;
  }

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
    return AcademicYear.builder()
        .id(yearDto.getId())
        .name(yearDto.getName())
        .startDate(DateUtils.getLocalDateFromString(yearDto.getStartDate()))
        .endDate(DateUtils.getLocalDateFromString(yearDto.getEndDate()))
        .school(School.fromDto(yearDto.getSchool()))
        .build();
  }
}

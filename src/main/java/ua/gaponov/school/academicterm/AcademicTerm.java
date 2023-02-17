package ua.gaponov.school.academicterm;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import ua.gaponov.school.academicyear.AcademicYear;
import ua.gaponov.school.utils.DateUtils;

@Entity
@Data
@Builder
public class AcademicTerm {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "term_name", nullable = false)
  private String name;

  @Column(name = "start_term", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_term", nullable = false)
  private LocalDate endDate;

  @ManyToOne
  @JoinColumn(name = "academic_year_id")
  private AcademicYear academicYear;

  public AcademicTerm() {

  }

  public AcademicTerm(int id, String name, LocalDate startDate, LocalDate endDate,
      AcademicYear academicYear) {
    this.id = id;
    this.name = name;
    this.startDate = startDate;
    this.endDate = endDate;
    this.academicYear = academicYear;
  }

  public static AcademicTermDto toDto(AcademicTerm academicTerm) {
    return AcademicTermDto.builder()
        .id(academicTerm.getId())
        .name(academicTerm.getName())
        .startDate(DateUtils.getStringFromLocalDate(academicTerm.getStartDate()))
        .endDate(DateUtils.getStringFromLocalDate(academicTerm.getEndDate()))
        .academicYear(AcademicYear.toDto(academicTerm.getAcademicYear()))
        .build();
  }

  public static AcademicTerm fromDto(AcademicTermDto academicTermDto) {
    return AcademicTerm.builder()
        .id(academicTermDto.getId())
        .name(academicTermDto.getName())
        .startDate(DateUtils.getLocalDateFromString(academicTermDto.getStartDate()))
        .endDate(DateUtils.getLocalDateFromString(academicTermDto.getEndDate()))
        .academicYear(AcademicYear.fromDto(academicTermDto.getAcademicYear()))
        .build();
  }
}

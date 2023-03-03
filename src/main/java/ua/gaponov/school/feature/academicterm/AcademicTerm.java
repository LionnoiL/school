package ua.gaponov.school.feature.academicterm;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import ua.gaponov.school.feature.academicyear.AcademicYear;
import ua.gaponov.school.model.NamedEntity;
import ua.gaponov.school.utils.DateUtils;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcademicTerm extends NamedEntity {

  @Column(name = "start_term", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_term", nullable = false)
  private LocalDate endDate;

  @ManyToOne
  @JoinColumn(name = "academic_year_id")
  private AcademicYear academicYear;

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
    AcademicTerm term = new AcademicTerm();
    term.setId(academicTermDto.getId());
    term.setName(academicTermDto.getName());
    term.setStartDate(DateUtils.getLocalDateFromString(academicTermDto.getStartDate()));
    term.setEndDate(DateUtils.getLocalDateFromString(academicTermDto.getEndDate()));
    term.setAcademicYear(AcademicYear.fromDto(academicTermDto.getAcademicYear()));
    return term;
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

  public AcademicYear getAcademicYear() {
    return academicYear;
  }

  public void setAcademicYear(AcademicYear academicYear) {
    this.academicYear = academicYear;
  }
}

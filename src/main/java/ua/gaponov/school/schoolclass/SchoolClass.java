package ua.gaponov.school.schoolclass;

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

@Entity
@Data
@Builder
public class SchoolClass {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(name = "number_class")
  private String name;
  @Column(name = "description")
  private String description;
  @ManyToOne
  @JoinColumn(name = "school_id")
  private School school;

  public SchoolClass() {
  }

  public SchoolClass(long id, String name, String description,
      School school) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.school = school;
  }

  public static SchoolClassDto toDto(SchoolClass schoolClass) {
    return SchoolClassDto.builder()
        .id(schoolClass.getId())
        .name(schoolClass.getName())
        .description(schoolClass.getDescription())
        .school(School.toDto(schoolClass.getSchool()))
        .build();
  }

  public static SchoolClass fromDto(SchoolClassDto schoolClassDto) {
    return SchoolClass.builder()
        .id(schoolClassDto.getId())
        .name(schoolClassDto.getName())
        .description(schoolClassDto.getDescription())
        .school(School.fromDto(schoolClassDto.getSchool()))
        .build();
  }
}

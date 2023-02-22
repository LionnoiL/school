package ua.gaponov.school.feature.school;

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
public class School {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "title", nullable = false)
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "address")
  private String address;

  public School(int id, String name, String description, String address) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.address = address;
  }

  public School() {
  }

  public static SchoolDto toDto(School school) {
    SchoolDto res = SchoolDto.builder()
        .name(school.getName())
        .address(school.getAddress())
        .description(school.getDescription())
        .id(school.getId())
        .build();
    return res;
  }

  public static School fromDto(SchoolDto schoolDto) {
    School school = School.builder()
        .name(schoolDto.getName())
        .address(schoolDto.getAddress())
        .description(schoolDto.getDescription())
        .id(schoolDto.getId())
        .build();
    return school;
  }
}

package ua.gaponov.school.feature.school;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import ua.gaponov.school.model.NamedEntity;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class School extends NamedEntity {

  @Column(name = "description")
  private String description;

  @Column(name = "address")
  private String address;

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
    School school = new School();
    school.setId(schoolDto.getId());
    school.setName(schoolDto.getName());
    school.setAddress(schoolDto.getAddress());
    school.setAddress(schoolDto.getAddress());
    school.setDescription(schoolDto.getDescription());
    return school;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}

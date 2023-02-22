package ua.gaponov.school.feature.school;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolDto {

  private int id;
  private String name;
  private String description;
  private String address;
}
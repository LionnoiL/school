package ua.gaponov.school.feature.users;

import lombok.Builder;
import lombok.Data;
import ua.gaponov.school.enums.ERole;

@Data
@Builder
public class UserDto {

  private long id;
  private String userName;
  private ERole role;
  private boolean enabled;
}

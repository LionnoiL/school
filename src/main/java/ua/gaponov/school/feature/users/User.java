package ua.gaponov.school.feature.users;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.gaponov.school.enums.ERole;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "username", nullable = false)
  private String userName;
  @Column(name = "password", nullable = false)
  private String password;
  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private ERole role;
  @Column(name = "enabled")
  private boolean enabled;
  @Transient
  private Collection<? extends GrantedAuthority> authorities;

  public User(int id, String userName, String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.userName = userName;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserDto toDto(User user){
    UserDto res = UserDto.builder()
        .id(user.getId())
        .role(user.getRole())
        .enabled(user.isEnabled())
        .build();
    res.setUserName(user.getUsername());
    return res;
  }

  public static User fromDto(UserDto userDto){
    return User.builder()
        .id(userDto.getId())
        .userName(userDto.getUserName())
        .role(userDto.getRole())
        .enabled(userDto.isEnabled())
        .build();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getUsername() {
    return userName;
  }

  @Override
  public String getPassword(){
    return password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
}



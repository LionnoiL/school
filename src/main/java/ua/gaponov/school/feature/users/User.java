package ua.gaponov.school.feature.users;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.gaponov.school.enums.ERole;
import ua.gaponov.school.model.BaseEntity;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

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

  public User(long id, String userName, String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.setId(id);
    this.userName = userName;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserDto toDto(User user) {
    UserDto res = UserDto.builder()
        .id(user.getId())
        .role(user.getRole())
        .enabled(user.isEnabled())
        .build();
    res.setUserName(user.getUsername());
    return res;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public ERole getRole() {
    return role;
  }

  public void setRole(ERole role) {
    this.role = role;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(
      Collection<? extends GrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  @Override
  public String getUsername() {
    return userName;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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



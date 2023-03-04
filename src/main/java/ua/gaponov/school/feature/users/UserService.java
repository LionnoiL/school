package ua.gaponov.school.feature.users;

import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

  private final UserRepo userRepo;

  public static User build(User user) {
    List<GrantedAuthority> authorities = List.of(
        new SimpleGrantedAuthority(user.getRole().toString()));
    return new User(user.getId(), user.getUsername(), user.getPassword(), authorities);
  }

  public List<User> getAll() {
    return userRepo.findAll();
  }

  public User findById(long id) throws NotFoundException {
    Optional<User> optional = userRepo.findById(id);
    if (optional.isEmpty()) {
      throw new NotFoundException("User not present");
    }
    return optional.get();
  }

  public void save(User user) {
    userRepo.save(user);
  }

  public void delete(User user) {
    userRepo.delete(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepo.findUserByUserName(username).orElseThrow(
        () -> new UsernameNotFoundException("User not found for username:" + username));
    return build(user);
  }
}

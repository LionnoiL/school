package ua.gaponov.school.feature.users;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepoBasic extends JpaRepository<User, Integer> {
  Optional<User> findUserByUserName(String username);
}

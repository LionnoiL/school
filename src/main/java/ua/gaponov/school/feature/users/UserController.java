package ua.gaponov.school.feature.users;

import static ua.gaponov.school.utils.UrlUtils.USERS_URL;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ua.gaponov.school.enums.ERole;

@RequiredArgsConstructor
@Controller
@RequestMapping(USERS_URL)
public class UserController {

  private final UserService userService;

  @GetMapping
  public ModelAndView list() {
    ModelAndView result = new ModelAndView("users/index");
    List<UserDto> users = userService.getAll().stream()
        .map(User::toDto)
        .sorted(Comparator.comparing(UserDto::getId))
        .collect(Collectors.toList());
    result.addObject("users", users);
    return result;
  }

  @PostMapping("/add")
  public RedirectView add(@RequestParam(value = "userName") String userName,
      @RequestParam(value = "password") String password,
      @RequestParam(value = "enabled", required = false) String enabled,
      @RequestParam(value = "userRole") String userRole) {

    User user = new User();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String hashedPassword = passwordEncoder.encode(password);
    user.setPassword(hashedPassword);
    user.setUserName(userName);
    user.setEnabled(enabled != null ? true : false);
    user.setRole("Admin".equals(userRole) ? ERole.ROLE_ADMIN : ERole.ROLE_USER);
    userService.save(user);
    return new RedirectView(USERS_URL);
  }
}

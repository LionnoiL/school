package ua.gaponov.school.feature.users;

import static ua.gaponov.school.utils.UrlUtils.USERS_URL;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ua.gaponov.school.enums.ERole;
import ua.gaponov.school.exception.StudentNotFoundException;
import ua.gaponov.school.exception.UserNotFoundException;

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
    user.setRole("Адміністратор".equals(userRole) ? ERole.ROLE_ADMIN : ERole.ROLE_USER);
    userService.save(user);
    return new RedirectView(USERS_URL);
  }

  @GetMapping("/{id}")
  public ModelAndView edit(@PathVariable(value = "id") int id) {
    ModelAndView result = new ModelAndView();
    UserDto userDto = null;
    try {
      userDto = User.toDto(userService.findById(id));
      result.setViewName("users/edit");
      String userRole = userDto.getRole().getFriendlyName();

      result.addObject("userRole", userRole);
      result.addObject("user", userDto);
    } catch (NotFoundException e) {
      result = new ModelAndView("users/not-found");
    }
    return result;
  }

  @PostMapping("/edit")
  public RedirectView editSchoolClass(@RequestParam(value = "id") int id,
      @RequestParam(value = "userName") String userName,
      @RequestParam(value = "password") String password,
      @RequestParam(value = "enabled", required = false) String enabled,
      @RequestParam(value = "userRole") String userRole) {
    User user = null;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String hashedPassword = passwordEncoder.encode(password);
    try {
      user = userService.findById(id);
      if (!password.isBlank()) {
        user.setPassword(hashedPassword);
      }
      user.setUserName(userName);
      user.setEnabled(enabled != null ? true : false);
      user.setRole("Адміністратор".equals(userRole) ? ERole.ROLE_ADMIN : ERole.ROLE_USER);
      userService.save(user);
    } catch (NotFoundException e) {
      throw new UserNotFoundException("User not found with id: " + id);
    }
    return new RedirectView(USERS_URL);
  }

  @PostMapping("/delete")
  public RedirectView delete(@RequestParam(value = "id") int id) {
    User user = null;
    try {
      user = userService.findById(id);
    } catch (NotFoundException e) {
      throw new StudentNotFoundException("User not found with id: " + id);
    }
    userService.delete(user);
    return new RedirectView(USERS_URL);
  }
}

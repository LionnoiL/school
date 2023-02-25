package ua.gaponov.school.feature.users;

import static ua.gaponov.school.utils.UrlUtils.USERS_URL;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
}

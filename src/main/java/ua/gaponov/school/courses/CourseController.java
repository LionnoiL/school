package ua.gaponov.school.courses;

import static ua.gaponov.school.utils.UrlUtils.COURSE_URL;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ua.gaponov.school.exception.CourseNotFoundException;

@RequiredArgsConstructor
@Controller
@RequestMapping(COURSE_URL)
public class CourseController {

  private final CourseService courseService;

  @GetMapping
  public ModelAndView list() {
    ModelAndView result = new ModelAndView("courses/index");
    List<CourseDto> list = courseService.getAll().stream()
        .map(Course::toDto)
        .sorted(Comparator.comparing(CourseDto::getId))
        .collect(Collectors.toList());
    result.addObject("courses", list);
    return result;
  }

  @PostMapping("/add")
  public RedirectView add(@RequestParam(value = "name") String name,
      @RequestParam(value = "description") String description,
      @RequestParam(value = "enabled", required = false) String enabled) {
    Course course = null;
    course = Course.builder()
        .name(name)
        .description(description)
        .active(enabled != null)
        .build();
    courseService.save(course);

    return new RedirectView(COURSE_URL);
  }

  @GetMapping("/{id}")
  public ModelAndView edit(@PathVariable(value = "id") int id) {
    ModelAndView result = new ModelAndView();
    CourseDto courseDto = null;

    try {
      courseDto = Course.toDto(courseService.findById(id));

      result.setViewName("courses/edit");
      result.addObject("course", courseDto);
    } catch (NotFoundException e) {
      result = new ModelAndView("courses/not-found");
    }

    return result;
  }

  @PostMapping("/edit")
  public RedirectView editSchool(@RequestParam(value = "id") int id,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "description") String description,
      @RequestParam(value = "enabled", required = false) String enabled) {
    Course course = null;

    try {
      course = courseService.findById(id);
      course.setName(name);
      course.setDescription(description);
      course.setActive(enabled != null);
      courseService.save(course);
    } catch (NotFoundException e) {
      throw new CourseNotFoundException("Course not found with id: " + id);
    }

    return new RedirectView(COURSE_URL);
  }

  @PostMapping("/delete")
  public RedirectView delete(@RequestParam(value = "id") int id) {
    Course course = null;
    try {
      course = courseService.findById(id);
      courseService.delete(course);
    } catch (NotFoundException e) {
      throw new CourseNotFoundException("Course not found with id: " + id);
    }

    return new RedirectView(COURSE_URL);
  }
}

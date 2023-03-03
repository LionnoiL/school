package ua.gaponov.school.feature.classcourses;

import static ua.gaponov.school.utils.UrlUtils.CLASS_COURSES_URL;
import static ua.gaponov.school.utils.UrlUtils.COURSE_URL;

import java.util.Set;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ua.gaponov.school.exception.CourseNotFoundException;
import ua.gaponov.school.exception.SchoolClassNotFoundException;
import ua.gaponov.school.feature.courses.Course;
import ua.gaponov.school.feature.courses.CourseService;
import ua.gaponov.school.feature.schoolclass.SchoolClass;
import ua.gaponov.school.feature.schoolclass.SchoolClassService;

@RequiredArgsConstructor
@Controller
@RequestMapping(CLASS_COURSES_URL)
public class ClassCoursesController {

  private final SchoolClassService schoolClassService;
  private final CourseService courseService;

  @PostMapping("/add")
  public RedirectView add(@RequestParam(value = "course_id") long courseId,
      @RequestParam(value = "class_id") long classId,
      @RequestParam(value = "return_path", required = false) String returnPath) {

    SchoolClass schoolClass;
    Course course;

    try {
      schoolClass = schoolClassService.findById(classId);
    } catch (NotFoundException e) {
      throw new SchoolClassNotFoundException("School class not found with id: " + classId);
    }
    try {
      course = courseService.findById(courseId);
    } catch (NotFoundException e) {
      throw new CourseNotFoundException("Course not found with id: " + courseId);
    }

    Set<Course> courses = schoolClass.getCourses();
    long count = courses.stream().filter(o -> course.equals(o)).count();
    if (count == 0) {
      courses.add(course);
      schoolClass.setCourses(courses);
      schoolClassService.save(schoolClass);
    }

    if (returnPath == null) {
      return new RedirectView(COURSE_URL);
    }

    return new RedirectView(returnPath);
  }

  @PostMapping("/delete")
  public RedirectView delete(@RequestParam(value = "course_id") long courseId,
      @RequestParam(value = "class_id") long classId,
      @RequestParam(value = "return_path", required = false) String returnPath) {

    SchoolClass schoolClass;
    Course course;

    try {
      schoolClass = schoolClassService.findById(classId);
    } catch (NotFoundException e) {
      throw new SchoolClassNotFoundException("School class not found with id: " + classId);
    }
    try {
      course = courseService.findById(courseId);
    } catch (NotFoundException e) {
      throw new CourseNotFoundException("Course not found with id: " + courseId);
    }

    Set<Course> courses = schoolClass.getCourses();

    courses.remove(course);
    schoolClass.setCourses(courses);
    schoolClassService.save(schoolClass);

    if (returnPath == null) {
      return new RedirectView(COURSE_URL);
    }

    return new RedirectView(returnPath);
  }
}

package ua.gaponov.school.feature.schoolclass;

import static ua.gaponov.school.utils.UrlUtils.SCHOOL_CLASS_URL;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
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
import ua.gaponov.school.exception.SchoolClassNotFoundException;
import ua.gaponov.school.feature.courses.CourseDto;
import ua.gaponov.school.feature.school.School;
import ua.gaponov.school.feature.school.SchoolService;

@RequiredArgsConstructor
@Controller
@RequestMapping(SCHOOL_CLASS_URL)
public class SchoolClassController {

  private final SchoolClassService schoolClassService;
  private final SchoolService schoolService;

  @GetMapping
  public ModelAndView list() {
    ModelAndView result = new ModelAndView("school-class/index");
    List<SchoolClassDto> classes = schoolClassService.getAll().stream()
        .map(SchoolClass::toDto)
        .sorted(Comparator.comparing(SchoolClassDto::getId))
        .collect(Collectors.toList());
    result.addObject("classes", classes);
    return result;
  }

  @PostMapping("/add")
  public RedirectView add(@RequestParam(value = "name") String name,
      @RequestParam(value = "description") String description,
      @RequestParam(value = "school_id") long schoolId,
      @RequestParam(value = "return_path", required = false) String returnPath) {

    SchoolClass schoolClass = new SchoolClass();
    School school;
    try {
      school = schoolService.findById(schoolId);
      schoolClass.setName(name);
      schoolClass.setDescription(description);
      schoolClass.setSchool(school);
      schoolClassService.save(schoolClass);
    } catch (NotFoundException e) {
      throw new SchoolClassNotFoundException("School class not found with id: " + schoolId);
    }

    if (returnPath == null) {
      return new RedirectView(SCHOOL_CLASS_URL);
    }

    return new RedirectView(returnPath);
  }

  @GetMapping("/{id}")
  public ModelAndView edit(@PathVariable(value = "id") long id) {
    ModelAndView result = new ModelAndView();
    SchoolClassDto schoolClassDto = null;

    try {
      schoolClassDto = SchoolClass.toDto(schoolClassService.findById(id));
      Set<CourseDto> courses = schoolClassDto.getCourses();

      result.setViewName("school-class/edit");

      result.addObject("schoolClass", schoolClassDto);
      result.addObject("courses", courses);
      result.addObject("returnPath", "/school-class/"+id);
    } catch (NotFoundException e) {
      result = new ModelAndView("school-class/not-found");
    }

    return result;
  }

  @PostMapping("/edit")
  public RedirectView editSchoolClass(@RequestParam(value = "id") long id,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "description") String description,
      @RequestParam(value = "school_id") long schoolId) {

    School school = null;
    try {
      school = schoolService.findById(schoolId);
      SchoolClass schoolClass = null;
      schoolClass = schoolClassService.findById(id);

      schoolClass.setSchool(school);
      schoolClass.setName(name);
      schoolClass.setDescription(description);
      schoolClassService.save(schoolClass);
    } catch (NotFoundException e) {
      throw new SchoolClassNotFoundException("School class not found with id: " + id);
    }

    return new RedirectView(SCHOOL_CLASS_URL);
  }

  @PostMapping("/delete")
  public RedirectView delete(@RequestParam(value = "id") long id) {
    SchoolClass schoolClass = null;
    try {
      schoolClass = schoolClassService.findById(id);
    } catch (NotFoundException e) {
      throw new SchoolClassNotFoundException("School class not found with id: " + id);
    }
    schoolClassService.delete(schoolClass);

    return new RedirectView(SCHOOL_CLASS_URL);
  }
}

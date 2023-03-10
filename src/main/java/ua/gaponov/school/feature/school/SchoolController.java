package ua.gaponov.school.feature.school;

import static ua.gaponov.school.utils.UrlUtils.SCHOOL_URL;

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
import ua.gaponov.school.feature.academicyear.AcademicYear;
import ua.gaponov.school.feature.academicyear.AcademicYearDto;
import ua.gaponov.school.feature.academicyear.AcademicYearService;
import ua.gaponov.school.exception.SchoolNotFoundException;

@RequiredArgsConstructor
@Controller
@RequestMapping(SCHOOL_URL)
public class SchoolController {

  private final SchoolService schoolService;
  private final AcademicYearService academicYearService;

  @GetMapping
  public ModelAndView list() {
    ModelAndView result = new ModelAndView("school/index");
    List<SchoolDto> list = schoolService.getAll().stream()
        .map(School::toDto)
        .sorted(Comparator.comparing(SchoolDto::getId))
        .collect(Collectors.toList());
    result.addObject("schools", list);
    return result;
  }

  @PostMapping("/add")
  public RedirectView add(@RequestParam(value = "name") String name,
      @RequestParam(value = "address") String address,
      @RequestParam(value = "description") String description) {
    School school = new School();
    school.setAddress(address);
    school.setName(name);
    school.setDescription(description);

    schoolService.save(school);
    return new RedirectView(SCHOOL_URL);
  }

  @GetMapping("/{id}")
  public ModelAndView edit(@PathVariable(value = "id") long id) {
    ModelAndView result = new ModelAndView();
    SchoolDto schoolDto = null;

    try {
      schoolDto = School.toDto(schoolService.findById(id));

      List<AcademicYearDto> years = academicYearService.getAllBySchool(id).stream()
          .map(AcademicYear::toDto)
          .sorted(Comparator.comparing(AcademicYearDto::getId))
          .collect(Collectors.toList());

      result.setViewName("school/edit");
      result.addObject("school", schoolDto);
      result.addObject("years", years);
      result.addObject("returnPath", "/school/"+id);
    } catch (NotFoundException e) {
      result = new ModelAndView("school/not-found");
    }

    return result;
  }

  @PostMapping("/edit")
  public RedirectView editSchool(@RequestParam(value = "id") long id,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "address") String address,
      @RequestParam(value = "description") String description) {
    School school = null;

    try {
      school = schoolService.findById(id);
      school.setAddress(address);
      school.setDescription(description);
      school.setName(name);
      schoolService.save(school);
    } catch (NotFoundException e) {
      throw new SchoolNotFoundException("School not found with id: " + id);
    }

    return new RedirectView(SCHOOL_URL);
  }

  @PostMapping("/delete")
  public RedirectView delete(@RequestParam(value = "id") long id) {
    School school = null;
    try {
      school = schoolService.findById(id);
      schoolService.delete(school);
    } catch (NotFoundException e) {
      throw new SchoolNotFoundException("School not found with id: " + id);
    }

    return new RedirectView(SCHOOL_URL);
  }
}

package ua.gaponov.school.feature.academicyear;

import static ua.gaponov.school.utils.UrlUtils.ACADEMIC_YEAR_URL;

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
import ua.gaponov.school.exception.AcademicYearNotFoundException;
import ua.gaponov.school.exception.SchoolNotFoundException;
import ua.gaponov.school.feature.school.School;
import ua.gaponov.school.feature.school.SchoolService;
import ua.gaponov.school.utils.DateUtils;

@RequiredArgsConstructor
@Controller
@RequestMapping(ACADEMIC_YEAR_URL)
public class AcademicYearController {

  private final AcademicYearService academicYearService;
  private final SchoolService schoolService;

  @GetMapping
  public ModelAndView list() {
    ModelAndView result = new ModelAndView("academic-year/index");
    List<AcademicYearDto> years = academicYearService.getAll().stream()
        .map(AcademicYear::toDto)
        .sorted(Comparator.comparing(AcademicYearDto::getId))
        .collect(Collectors.toList());
    result.addObject("years", years);
    return result;
  }


  @PostMapping("/add")
  public RedirectView add(@RequestParam(value = "name") String name,
      @RequestParam(value = "startDateAddYear") String startDate,
      @RequestParam(value = "endDateAddYear") String endDate,
      @RequestParam(value = "school_id") long schoolId,
      @RequestParam(value = "return_path", required = false) String returnPath) {

    AcademicYear year = new AcademicYear();
    School school = null;
    try {
      school = schoolService.findById(schoolId);
      year.setName(name);
      year.setStartDate(DateUtils.getLocalDateFromString(startDate));
      year.setEndDate(DateUtils.getLocalDateFromString(endDate));
      year.setSchool(school);
      academicYearService.save(year);
    } catch (NotFoundException e) {
      throw new SchoolNotFoundException("School not found with id: " + schoolId);
    }

    if (returnPath == null) {
      return new RedirectView(ACADEMIC_YEAR_URL);
    }

    return new RedirectView(returnPath);
  }

  @GetMapping("/{id}")
  public ModelAndView edit(@PathVariable(value = "id") long id,
      @RequestParam(value = "back", required = false) String back) {
    ModelAndView result = new ModelAndView();
    AcademicYearDto academicYearDto = null;

    try {
      academicYearDto = AcademicYear.toDto(academicYearService.findById(id));
      result.setViewName("academic-year/edit");

      result.addObject("year", academicYearDto);
    } catch (NotFoundException e) {
      result = new ModelAndView("academic-year/not-found");
    }

    return result;
  }

  @PostMapping("/edit")
  public RedirectView editYear(@RequestParam(value = "id") long id,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "startDateEditYear") String startDate,
      @RequestParam(value = "endDateEditYear") String endDate,
      @RequestParam(value = "school_id") long schoolId) {

    School school = null;
    try {
      school = schoolService.findById(schoolId);
      AcademicYear academicYear = null;
      academicYear = academicYearService.findById(id);

      academicYear.setSchool(school);
      academicYear.setName(name);
      academicYear.setStartDate(DateUtils.getLocalDateFromString(startDate));
      academicYear.setEndDate(DateUtils.getLocalDateFromString(endDate));
      academicYearService.save(academicYear);
    } catch (NotFoundException e) {
      throw new AcademicYearNotFoundException("Academic year not found with id: " + id);
    }

    return new RedirectView(ACADEMIC_YEAR_URL);
  }

  @PostMapping("/delete")
  public RedirectView delete(@RequestParam(value = "id") long id) {
    AcademicYear academicYear = null;
    try {
      academicYear = academicYearService.findById(id);
    } catch (NotFoundException e) {
      throw new AcademicYearNotFoundException("Academic year not found with id: " + id);
    }
    academicYearService.delete(academicYear);

    return new RedirectView(ACADEMIC_YEAR_URL);
  }
}

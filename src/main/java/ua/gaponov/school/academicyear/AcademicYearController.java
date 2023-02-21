package ua.gaponov.school.academicyear;

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
import ua.gaponov.school.school.School;
import ua.gaponov.school.school.SchoolDto;
import ua.gaponov.school.school.SchoolService;
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
      @RequestParam(value = "school_id") int schoolId,
      @RequestParam(value = "return_controller", required = false) String controller,
      @RequestParam(value = "return_params", required = false) String params) {

    AcademicYear academicYear = null;
    School school = null;
    try {
      school = schoolService.findById(schoolId);
      academicYear = AcademicYear.builder()
          .name(name)
          .startDate(DateUtils.getLocalDateFromString(startDate))
          .endDate(DateUtils.getLocalDateFromString(endDate))
          .school(school)
          .build();
      academicYearService.save(academicYear);
    } catch (NotFoundException e) {
      throw new SchoolNotFoundException("School not found with id: " + schoolId);
    }

    if (controller==null) {
      return new RedirectView(ACADEMIC_YEAR_URL);
    }

    if (params==null){
      params = "";
    }

    return new RedirectView("/"+controller+"/"+params);
  }

  @GetMapping("/{id}")
  public ModelAndView edit(@PathVariable(value = "id") int id,
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
  public RedirectView editYear(@RequestParam(value = "id") int id,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "startDateEditYear") String startDate,
      @RequestParam(value = "endDateEditYear") String endDate,
      @RequestParam(value = "school_id") int schoolId) {

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
  public RedirectView delete(@RequestParam(value = "id") int id) {
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

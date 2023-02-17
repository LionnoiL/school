package ua.gaponov.school.academicyear;

import static ua.gaponov.school.utils.UrlUtils.ACADEMIC_YEAR_URL;

import java.util.List;
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
    List<AcademicYearDto> list = academicYearService.getAll();
    List<SchoolDto> schools = schoolService.getAll();

    result.addObject("years", list);
    result.addObject("schools", schools);

    return result;
  }


  @PostMapping("/add")
  public RedirectView add(@RequestParam(value = "name") String name,
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate,
      @RequestParam(value = "school_id") int schoolId) {

    AcademicYearDto academicYearDto = null;
    SchoolDto school = null;
    try {
      school = schoolService.findById(schoolId);
      academicYearDto = AcademicYearDto.builder()
          .name(name)
          .startDate(DateUtils.getSqlFormaDateFromEuropean(startDate))
          .endDate(DateUtils.getSqlFormaDateFromEuropean(endDate))
          .school(school)
          .build();
      academicYearService.save(academicYearDto);
    } catch (NotFoundException e) {
      throw new SchoolNotFoundException("School not found with id: " + schoolId);
    }

    return new RedirectView(ACADEMIC_YEAR_URL);
  }

  @GetMapping("/edit/{id}")
  public ModelAndView edit(@PathVariable(value = "id") int id) {
    ModelAndView result = new ModelAndView();
    AcademicYearDto academicYearDto = null;

    try {
      academicYearDto = academicYearService.findById(id);

      result.setViewName("academic-year/edit");
      List<SchoolDto> schools = schoolService.getAll();
      result.addObject("year", academicYearDto);
      result.addObject("schools", schools);
    } catch (NotFoundException e) {
      result = new ModelAndView("academic-year/not-found");
    }

    return result;
  }

  @PostMapping("/edit")
  public RedirectView editYear(@RequestParam(value = "id") int id,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate,
      @RequestParam(value = "school_id") int schoolId) {

    SchoolDto school = null;
    try {
      school = schoolService.findById(schoolId);
      AcademicYearDto academicYearDto = null;
      academicYearDto = academicYearService.findById(id);

      academicYearDto.setSchool(school);
      academicYearDto.setName(name);
      academicYearDto.setStartDate(startDate);
      academicYearDto.setEndDate(endDate);
      academicYearService.save(academicYearDto);
    } catch (NotFoundException e) {
      throw new AcademicYearNotFoundException("Academic year not found with id: " + id);
    }

    return new RedirectView(ACADEMIC_YEAR_URL);
  }

  @PostMapping("/delete")
  public RedirectView delete(@RequestParam(value = "id") int id) {
    AcademicYearDto academicYearDto = null;
    try {
      academicYearDto = academicYearService.findById(id);
    } catch (NotFoundException e) {
      throw new AcademicYearNotFoundException("Academic year not found with id: " + id);
    }
    academicYearService.delete(academicYearDto);

    return new RedirectView(ACADEMIC_YEAR_URL);
  }
}

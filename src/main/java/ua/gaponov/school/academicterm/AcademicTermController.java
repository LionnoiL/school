package ua.gaponov.school.academicterm;

import static ua.gaponov.school.utils.UrlUtils.ACADEMIC_TERM_URL;
import static ua.gaponov.school.utils.UrlUtils.ACADEMIC_YEAR_URL;

import java.util.List;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ua.gaponov.school.academicyear.AcademicYearDto;
import ua.gaponov.school.academicyear.AcademicYearService;
import ua.gaponov.school.exception.SchoolNotFoundException;
import ua.gaponov.school.school.SchoolDto;
import ua.gaponov.school.utils.DateUtils;

@RequiredArgsConstructor
@Controller
@RequestMapping(ACADEMIC_TERM_URL)
public class AcademicTermController {

  private final AcademicTermService academicTermService;
  private final AcademicYearService academicYearService;

  @GetMapping
  public ModelAndView list() {
    ModelAndView result = new ModelAndView("academic-term/index");
    List<AcademicTermDto> list = academicTermService.getAll();
    List<AcademicYearDto> years = academicYearService.getAll();

    result.addObject("terms", list);
    result.addObject("years", years);

    return result;
  }

  @PostMapping("/add")
  public RedirectView add(@RequestParam(value = "name") String name,
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate,
      @RequestParam(value = "academic_year_id") int yearId) {

    AcademicTermDto academicTermDto = null;
    AcademicYearDto academicYearDto = null;
    try {
      academicYearDto = academicYearService.findById(yearId);
      academicTermDto = AcademicTermDto.builder()
          .name(name)
          .startDate(DateUtils.getSqlFormaDateFromEuropean(startDate))
          .endDate(DateUtils.getSqlFormaDateFromEuropean(endDate))
          .academicYear(academicYearDto)
          .build();
      academicTermService.save(academicTermDto);
    } catch (NotFoundException e) {
      throw new SchoolNotFoundException("Academic year not found with id: " + yearId);
    }

    return new RedirectView(ACADEMIC_TERM_URL);
  }
}

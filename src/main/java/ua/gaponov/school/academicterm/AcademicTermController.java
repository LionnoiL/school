package ua.gaponov.school.academicterm;

import static ua.gaponov.school.utils.UrlUtils.ACADEMIC_TERM_URL;

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
import ua.gaponov.school.academicyear.AcademicYear;
import ua.gaponov.school.academicyear.AcademicYearDto;
import ua.gaponov.school.academicyear.AcademicYearService;
import ua.gaponov.school.exception.AcademicTermNotFoundException;
import ua.gaponov.school.exception.SchoolNotFoundException;
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

    List<AcademicTermDto> list = academicTermService.getAll().stream()
        .map(AcademicTerm::toDto)
        .sorted(Comparator.comparing(AcademicTermDto::getId))
        .collect(Collectors.toList());

    result.addObject("terms", list);
    return result;
  }

  @PostMapping("/add")
  public RedirectView add(@RequestParam(value = "name") String name,
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate,
      @RequestParam(value = "academic_year_id") int yearId) {

    AcademicTerm academicTerm = null;
    AcademicYear year = null;
    try {
      year = academicYearService.findById(yearId);
      academicTerm = AcademicTerm.builder()
          .name(name)
          .startDate(DateUtils.getLocalDateFromString(startDate))
          .endDate(DateUtils.getLocalDateFromString(endDate))
          .academicYear(year)
          .build();
      academicTermService.save(academicTerm);
    } catch (NotFoundException e) {
      throw new SchoolNotFoundException("Academic year not found with id: " + yearId);
    }

    return new RedirectView(ACADEMIC_TERM_URL);
  }

  @GetMapping("/{id}")
  public ModelAndView edit(@PathVariable(value = "id") int id) {
    ModelAndView result = new ModelAndView();
    AcademicTermDto academicTermDto = null;

    try {
      academicTermDto = AcademicTerm.toDto(academicTermService.findById(id));

      result.setViewName("academic-term/edit");
      List<AcademicYearDto> years = academicYearService.getAll().stream()
          .map(AcademicYear::toDto)
          .sorted(Comparator.comparing(yearDto -> (Integer) yearDto.getId()))
          .collect(Collectors.toList());

      result.addObject("term", academicTermDto);
      result.addObject("years", years);
    } catch (NotFoundException e) {
      result = new ModelAndView("academic-term/not-found");
    }

    return result;
  }

  @PostMapping("/edit")
  public RedirectView editYear(@RequestParam(value = "id") int id,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate,
      @RequestParam(value = "academic_year_id") int yearId) {

    AcademicYear year = null;
    try {
      year = academicYearService.findById(yearId);

      AcademicTerm academicTerm = null;
      academicTerm = academicTermService.findById(id);

      academicTerm.setAcademicYear(year);
      academicTerm.setName(name);
      academicTerm.setStartDate(DateUtils.getLocalDateFromString(startDate));
      academicTerm.setEndDate(DateUtils.getLocalDateFromString(endDate));
      academicTermService.save(academicTerm);
    } catch (NotFoundException e) {
      throw new AcademicTermNotFoundException("Academic term not found with id: " + id);
    }

    return new RedirectView(ACADEMIC_TERM_URL);
  }

  @PostMapping("/delete")
  public RedirectView delete(@RequestParam(value = "id") int id) {
    AcademicTerm academicTermDto = null;
    try {
      academicTermDto = academicTermService.findById(id);
    } catch (NotFoundException e) {
      throw new AcademicTermNotFoundException("Academic term not found with id: " + id);
    }
    academicTermService.delete(academicTermDto);

    return new RedirectView(ACADEMIC_TERM_URL);
  }
}

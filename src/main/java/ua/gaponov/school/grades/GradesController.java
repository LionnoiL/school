package ua.gaponov.school.grades;

import static ua.gaponov.school.utils.UrlUtils.GRADES_URL;

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
import ua.gaponov.school.exception.GradeNotFoundException;

@RequiredArgsConstructor
@Controller
@RequestMapping(GRADES_URL)
public class GradesController {

  private final GradesService gradesService;

  @GetMapping
  public ModelAndView list() {
    ModelAndView result = new ModelAndView("grades/index");
    List<GradesDto> list = gradesService.getAll().stream()
        .map(Grades::toDto)
        .sorted(Comparator.comparing(GradesDto::getName))
        .collect(Collectors.toList());
    result.addObject("grades", list);
    return result;
  }

  @PostMapping("/add")
  public RedirectView add(@RequestParam(value = "name") String name,
      @RequestParam(value = "rate") double rate) {
    Grades grades = null;
    grades = Grades.builder()
        .name(name)
        .rate(rate)
        .build();
    gradesService.save(grades);

    return new RedirectView(GRADES_URL);
  }

  @GetMapping("/edit/{id}")
  public ModelAndView edit(@PathVariable(value = "id") int id) {
    ModelAndView result = new ModelAndView();
    GradesDto gradesDto = null;

    try {
      gradesDto = Grades.toDto(gradesService.findById(id));

      result.setViewName("grades/edit");
      result.addObject("grade", gradesDto);
    } catch (NotFoundException e) {
      result = new ModelAndView("grades/not-found");
    }

    return result;
  }

  @PostMapping("/edit")
  public RedirectView editSchool(@RequestParam(value = "id") int id,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "rate") double rate) {
    Grades grades = null;

    try {
      grades = gradesService.findById(id);
      grades.setName(name);
      grades.setRate(rate);
      gradesService.save(grades);
    } catch (NotFoundException e) {
      throw new GradeNotFoundException("Grade not found with id: " + id);
    }

    return new RedirectView(GRADES_URL);
  }

  @PostMapping("/delete")
  public RedirectView delete(@RequestParam(value = "id") int id) {
    Grades grades = null;
    try {
      grades = gradesService.findById(id);
      gradesService.delete(grades);
    } catch (NotFoundException e) {
      throw new GradeNotFoundException("Grade not found with id: " + id);
    }

    return new RedirectView(GRADES_URL);
  }
}

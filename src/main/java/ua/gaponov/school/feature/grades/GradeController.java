package ua.gaponov.school.feature.grades;

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
public class GradeController {

  private final GradeService gradeService;

  @GetMapping
  public ModelAndView list() {
    ModelAndView result = new ModelAndView("grades/index");
    List<GradeDto> list = gradeService.getAll().stream()
        .map(Grade::toDto)
        .sorted(Comparator.comparing(GradeDto::getId))
        .collect(Collectors.toList());
    result.addObject("grades", list);
    return result;
  }

  @PostMapping("/add")
  public RedirectView add(@RequestParam(value = "name") String name,
      @RequestParam(value = "rate") double rate) {
    Grade grade = new Grade();
    grade.setName(name);
    grade.setRate(rate);
    gradeService.save(grade);
    return new RedirectView(GRADES_URL);
  }

  @GetMapping("/{id}")
  public ModelAndView edit(@PathVariable(value = "id") long id) {
    ModelAndView result = new ModelAndView();
    GradeDto gradeDto = null;

    try {
      gradeDto = Grade.toDto(gradeService.findById(id));

      result.setViewName("grades/edit");
      result.addObject("grade", gradeDto);
    } catch (NotFoundException e) {
      result = new ModelAndView("grades/not-found");
    }

    return result;
  }

  @PostMapping("/edit")
  public RedirectView editSchool(@RequestParam(value = "id") long id,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "rate") double rate) {
    Grade grade = null;

    try {
      grade = gradeService.findById(id);
      grade.setName(name);
      grade.setRate(rate);
      gradeService.save(grade);
    } catch (NotFoundException e) {
      throw new GradeNotFoundException("Grade not found with id: " + id);
    }

    return new RedirectView(GRADES_URL);
  }

  @PostMapping("/delete")
  public RedirectView delete(@RequestParam(value = "id") long id) {
    Grade grade = null;
    try {
      grade = gradeService.findById(id);
      gradeService.delete(grade);
    } catch (NotFoundException e) {
      throw new GradeNotFoundException("Grade not found with id: " + id);
    }

    return new RedirectView(GRADES_URL);
  }
}

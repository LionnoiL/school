package ua.gaponov.school.school;

import static ua.gaponov.school.utils.UrlUtils.SCHOOL_URL;

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
import ua.gaponov.school.exception.SchoolNotFoundException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/school")
public class SchoolController {

  private final SchoolService service;

  @GetMapping
  public ModelAndView list() {
    ModelAndView result = new ModelAndView("school/index");
    List<SchoolDto> list = service.getAll();
    result.addObject("schools", list);
    return result;
  }

  @PostMapping("/add")
  public RedirectView add(@RequestParam(value = "name") String name,
      @RequestParam(value = "address") String address,
      @RequestParam(value = "description") String description) {
    SchoolDto schoolDto = null;
    schoolDto = SchoolDto.builder()
        .address(address)
        .name(name)
        .description(description)
        .build();
    service.save(schoolDto);

    return new RedirectView(SCHOOL_URL);
  }

  @GetMapping("/edit/{id}")
  public ModelAndView edit(@PathVariable(value = "id") int id) {
    ModelAndView result = new ModelAndView();
    SchoolDto schoolDto = null;

    try {
      schoolDto = service.findById(id);

      result.setViewName("school/edit");
      result.addObject("school", schoolDto);
    } catch (NotFoundException e) {
      result = new ModelAndView("school/not-found");
    }

    return result;
  }

  @PostMapping("/edit")
  public RedirectView editSchool(@RequestParam(value = "id") int id,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "address") String address,
      @RequestParam(value = "description") String description) {
    SchoolDto schoolDto = null;

    try {
      schoolDto = service.findById(id);
      schoolDto.setAddress(address);
      schoolDto.setDescription(description);
      schoolDto.setName(name);
      service.save(schoolDto);
    } catch (NotFoundException e) {
      throw new SchoolNotFoundException("School not found with id: " + id);
    }

    return new RedirectView(SCHOOL_URL);
  }

  @PostMapping("/delete")
  public RedirectView delete(@RequestParam(value = "id") int id) {
    SchoolDto schoolDto = null;
    try {
      schoolDto = service.findById(id);
      service.delete(schoolDto);
    } catch (NotFoundException e) {
      throw new SchoolNotFoundException("School not found with id: " + id);
    }

    return new RedirectView(SCHOOL_URL);
  }
}

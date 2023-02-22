package ua.gaponov.school.feature.students;

import static ua.gaponov.school.utils.UrlUtils.SCHOOL_CLASS_URL;
import static ua.gaponov.school.utils.UrlUtils.STUDENT_URL;

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
import ua.gaponov.school.exception.SchoolClassNotFoundException;
import ua.gaponov.school.exception.StudentNotFoundException;
import ua.gaponov.school.feature.school.School;
import ua.gaponov.school.feature.school.SchoolService;
import ua.gaponov.school.feature.schoolclass.SchoolClass;
import ua.gaponov.school.feature.schoolclass.SchoolClassDto;
import ua.gaponov.school.feature.schoolclass.SchoolClassService;

@RequiredArgsConstructor
@Controller
@RequestMapping(STUDENT_URL)
public class StudentController {

  private final StudentService studentService;
  private final SchoolClassService schoolClassService;

  @GetMapping
  public ModelAndView list() {
    ModelAndView result = new ModelAndView("students/index");
    List<StudentDto> students = studentService.getAll().stream()
        .map(Student::toDto)
        .sorted(Comparator.comparing(StudentDto::getId))
        .collect(Collectors.toList());
    result.addObject("students", students);
    return result;
  }

  @PostMapping("/add")
  public RedirectView add(@RequestParam(value = "firstName") String firstName,
      @RequestParam(value = "lastName") String lastName,
      @RequestParam(value = "class_id") int class_id,
      @RequestParam(value = "return_path", required = false) String returnPath) {

    Student student = null;
    SchoolClass schoolClass = null;
    try {
      schoolClass = schoolClassService.findById(class_id);
      student = Student.builder()
          .firstName(firstName)
          .lastName(lastName)
          .schoolClass(schoolClass)
          .build();
      studentService.save(student);
    } catch (NotFoundException e) {
      throw new SchoolClassNotFoundException("School class not found with id: " + class_id);
    }

    if (returnPath == null) {
      return new RedirectView(STUDENT_URL);
    }

    return new RedirectView(returnPath);
  }

  @GetMapping("/{id}")
  public ModelAndView edit(@PathVariable(value = "id") int id) {
    ModelAndView result = new ModelAndView();
    StudentDto studentDto = null;

    try {
      studentDto = Student.toDto(studentService.findById(id));
      result.setViewName("students/edit");

      result.addObject("student", studentDto);
    } catch (NotFoundException e) {
      result = new ModelAndView("students/not-found");
    }

    return result;
  }

  @PostMapping("/edit")
  public RedirectView editSchoolClass(@RequestParam(value = "id") int id,
      @RequestParam(value = "firstName") String firstName,
      @RequestParam(value = "lastName") String lastName,
      @RequestParam(value = "class_id") int class_id) {

    SchoolClass schoolClass = null;
    Student student = null;
    try {
      schoolClass = schoolClassService.findById(class_id);
      student = studentService.findById(id);

      student.setSchoolClass(schoolClass);
      student.setFirstName(firstName);
      student.setLastName(lastName);
      studentService.save(student);
    } catch (NotFoundException e) {
      throw new SchoolClassNotFoundException("Student found with id: " + id);
    }

    return new RedirectView(STUDENT_URL);
  }

  @PostMapping("/delete")
  public RedirectView delete(@RequestParam(value = "id") int id) {
    Student student = null;
    try {
      student = studentService.findById(id);
    } catch (NotFoundException e) {
      throw new StudentNotFoundException("Student not found with id: " + id);
    }
    studentService.delete(student);

    return new RedirectView(STUDENT_URL);
  }
}

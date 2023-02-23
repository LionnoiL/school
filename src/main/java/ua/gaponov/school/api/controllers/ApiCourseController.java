package ua.gaponov.school.api.controllers;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.gaponov.school.feature.courses.Course;
import ua.gaponov.school.feature.courses.CourseDto;
import ua.gaponov.school.feature.courses.CourseService;

@RestController
@RequestMapping(value = "/api/v1/course")
public class ApiCourseController {

  @Autowired
  private CourseService courseService;

  @GetMapping(value = {""}, produces = {"application/json"})
  public ResponseEntity<List<CourseDto>> getAllCourses(
      @RequestParam("search") Optional<String> keywords) {
    String k = (keywords.orElse(""));
    return ResponseEntity.ok().body(
        courseService.getAll(k).stream()
            .map(Course::toDto)
            .sorted(Comparator.comparing(CourseDto::getId))
            .collect(Collectors.toList()));
  }
}

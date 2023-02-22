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
import ua.gaponov.school.feature.school.School;
import ua.gaponov.school.feature.school.SchoolDto;
import ua.gaponov.school.feature.school.SchoolService;

@RestController
@RequestMapping(value = "/api/v1/schools")
public class ApiSchoolController {

  @Autowired
  private SchoolService schoolService;

  @GetMapping(value = {""}, produces = {"application/json"})
  public ResponseEntity<List<SchoolDto>> getAllSchool(
      @RequestParam("search") Optional<String> keywords) {
    String k = (keywords.orElse(""));
    return ResponseEntity.ok().body(
        schoolService.getAll(k).stream()
            .map(School::toDto)
            .sorted(Comparator.comparing(SchoolDto::getId))
            .collect(Collectors.toList()));
  }
}

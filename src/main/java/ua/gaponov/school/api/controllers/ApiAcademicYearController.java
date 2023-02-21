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
import ua.gaponov.school.academicyear.AcademicYear;
import ua.gaponov.school.academicyear.AcademicYearDto;
import ua.gaponov.school.academicyear.AcademicYearService;

@RestController
@RequestMapping(value = "/api/v1/year")
public class ApiAcademicYearController {

  @Autowired
  private AcademicYearService yearService;

  @GetMapping(value = {""}, produces = {"application/json"})
  public ResponseEntity<List<AcademicYearDto>> getAllYears(
      @RequestParam("search") Optional<String> keywords) {
    String k = (keywords.orElse(""));
    return ResponseEntity.ok().body(
        yearService.getAll(k).stream()
            .map(AcademicYear::toDto)
            .sorted(Comparator.comparing(AcademicYearDto::getId))
            .collect(Collectors.toList()));
  }
}

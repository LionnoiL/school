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
import ua.gaponov.school.feature.schoolclass.SchoolClass;
import ua.gaponov.school.feature.schoolclass.SchoolClassDto;
import ua.gaponov.school.feature.schoolclass.SchoolClassService;

@RestController
@RequestMapping(value = "/api/v1/classes")
public class ApiSchoolClassController {

  @Autowired
  private SchoolClassService schoolClassService;

  @GetMapping(value = {""}, produces = {"application/json"})
  public ResponseEntity<List<SchoolClassDto>> getAllSchoolClasses(
      @RequestParam("search") Optional<String> keywords,
      @RequestParam("school_id") Optional<Integer> school_id) {
    String k = (keywords.orElse(""));

    if (school_id.isPresent()) {
      return ResponseEntity.ok().body(
          schoolClassService.getAllBySchoolId(k, school_id.get()).stream()
              .map(SchoolClass::toDto)
              .sorted(Comparator.comparing(SchoolClassDto::getId))
              .collect(Collectors.toList()));
    }

    return ResponseEntity.ok().body(
        schoolClassService.getAll(k).stream()
            .map(SchoolClass::toDto)
            .sorted(Comparator.comparing(SchoolClassDto::getId))
            .collect(Collectors.toList()));
  }
}

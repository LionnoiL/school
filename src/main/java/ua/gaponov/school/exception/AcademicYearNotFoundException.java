package ua.gaponov.school.exception;

public class AcademicYearNotFoundException extends RuntimeException{
  public AcademicYearNotFoundException(String message) {
    super("No such academic year: " + message);
  }
}

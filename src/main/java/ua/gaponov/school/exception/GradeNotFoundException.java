package ua.gaponov.school.exception;

public class GradeNotFoundException extends RuntimeException{
  public GradeNotFoundException(String message) {
    super("No such grade: " + message);
  }
}

package ua.gaponov.school.exception;

public class AcademicTermNotFoundException extends RuntimeException{
  public AcademicTermNotFoundException(String message) {
    super("No such academic term: " + message);
  }
}

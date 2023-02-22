package ua.gaponov.school.exception;

public class StudentNotFoundException extends RuntimeException{
  public StudentNotFoundException(String message) {
    super("No such student: " + message);
  }
}

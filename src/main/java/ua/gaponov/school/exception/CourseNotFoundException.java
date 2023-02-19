package ua.gaponov.school.exception;

public class CourseNotFoundException extends RuntimeException{
  public CourseNotFoundException(String message) {
    super("No such course: " + message);
  }
}

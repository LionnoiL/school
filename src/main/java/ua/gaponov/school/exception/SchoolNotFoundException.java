package ua.gaponov.school.exception;

public class SchoolNotFoundException extends RuntimeException{
  public SchoolNotFoundException(String message) {
    super("No such school: " + message);
  }
}

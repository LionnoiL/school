package ua.gaponov.school.exception;

public class SchoolClassNotFoundException extends RuntimeException{
  public SchoolClassNotFoundException(String message) {
    super("No such school class: " + message);
  }
}

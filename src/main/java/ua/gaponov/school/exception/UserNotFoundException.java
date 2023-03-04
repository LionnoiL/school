package ua.gaponov.school.exception;

public class UserNotFoundException extends RuntimeException{
  public UserNotFoundException(String message) {
    super("No such user: " + message);
  }
}

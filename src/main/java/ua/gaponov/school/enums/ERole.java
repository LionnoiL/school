package ua.gaponov.school.enums;

public enum ERole {
  ROLE_ADMIN("Адміністратор"),
  ROLE_USER("Користувач");

  private String friendlyName;

  ERole(String friendlyName) {
    this.friendlyName = friendlyName;
  }

  public String getFriendlyName() {
    return friendlyName;
  }
}

package ua.gaponov.school.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

  private DateUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static LocalDate getLocalDateFromString(String stringDate) {
    DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    return LocalDate.from(europeanDateFormatter.parse(stringDate));
  }

  public static String getStringFromLocalDate(LocalDate date) {
    String europeanDatePattern = "dd.MM.yyyy";
    DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);
    return europeanDateFormatter.format(date);
  }

  public static String getSqlFormaDateFromEuropean(String stringDate) {
    LocalDate localDate = getLocalDateFromString(stringDate);
    return getStringFromLocalDate(localDate);
  }
}

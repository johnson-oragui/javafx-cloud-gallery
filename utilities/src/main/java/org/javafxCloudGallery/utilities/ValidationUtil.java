package org.javafxCloudGallery.utilities;

import java.util.regex.Pattern;

public class ValidationUtil {
  private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

  public static Tuple<String, String, String> validator(String username, String email, String password)
      throws Exception {
    Tuple<String, String, String> tuple = new Tuple<>();

    if (username != null) {
      checkLength(username, "username", 3, 50);
      hasInvalidChar(username, "username", "~!@#$%^&*()_+=-,./?>|<':\"");
      tuple.setUsername(username);
    }

    if (email != null) {
      hasInvalidChar(email, "email", "~!#$%^&*()+=,/?>|<':\"");
      emailValidator(email);
      tuple.setEmail(email);
    }

    if (password != null) {
      isValidPassword(password, 8);
      tuple.setPassword(password);
    }

    return tuple;
  }

  private static void hasInvalidChar(String target, String targetName, String invalidChars) throws Exception {
    for (Character c : target.toCharArray()) {
      if (c == ' ') {
        StringBuilder message = new StringBuilder();
        message.append(targetName);
        message.append(" must not contain white space char");
        throw new Exception(message.toString());
      }
      if (invalidChars != null && target.contains(invalidChars)) {
        StringBuilder message = new StringBuilder();
        message.append("special characters not allowed in ");
        message.append(targetName);
        throw new Exception(message.toString());
      }
    }
    return;
  }

  private static void checkLength(String target, String targetName, int minSize, int maxSize) throws Exception {
    if (target.length() < minSize) {
      StringBuilder message = new StringBuilder();
      message.append(targetName);
      message.append(" must have length upto ");
      message.append(minSize);
      message.append(" and not more than ");
      message.append(maxSize);
      throw new Exception(message.toString());
    }
  }

  private static void emailValidator(String email) throws Exception {
    if (!EMAIL_PATTERN.matcher(email).matches()) {
      throw new Exception("invalid email");
    }
  }

  private static void isValidPassword(String password, int minSize) throws Exception {
    String message = "password must have one Upper and Lower case, one digit, a special character, and not less than 8 in lentgth";
    if (password == null || password.length() < minSize)
      throw new Exception(message);

    boolean hasUpper = false, hasLower = false, hasDigit = false, hasAllowedChar = false;
    String allowedChar = "@!#&$()";

    for (char c : password.toCharArray()) {
      if (Character.isUpperCase(c)) {
        hasUpper = true;
      } else if (Character.isLowerCase(c)) {
        hasLower = true;
      } else if (Character.isDigit(c)) {
        hasDigit = true;
      } else if (allowedChar.indexOf(c) >= 0) {
        hasAllowedChar = true;
      } else {
        throw new Exception("Invalid special character");
      }

    }

    if (!(hasAllowedChar && hasDigit && hasLower && hasUpper)) {
      throw new Exception(message);
    }
  }

}

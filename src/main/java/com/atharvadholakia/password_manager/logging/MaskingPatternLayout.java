package com.atharvadholakia.password_manager.logging;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaskingPatternLayout extends PatternLayout {

  @Override
  public String doLayout(ILoggingEvent event) {
    String originalMessage = super.doLayout(event);
    String maskedMessage = maskSensitiveData(originalMessage);

    return maskedMessage;
  }

  private String maskSensitiveData(String message) {

    String regex = "(?i)(password\\s*[:=]\\s*)(\\S+)";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(message);

    StringBuilder masked = new StringBuilder();

    while (matcher.find()) {
      String beforePassword = matcher.group(1);
      String passwordValue = matcher.group(2);
      String maskedPassword = "*".repeat(passwordValue.length() - 1);
      matcher.appendReplacement(masked, beforePassword + maskedPassword);
    }
    matcher.appendTail(masked);

    return masked.toString();
  }
}

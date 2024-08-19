package com.atharvadholakia.password_manager.validation;

import com.atharvadholakia.password_manager.validation.annotation.ValidStringType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StringTypeValidator implements ConstraintValidator<ValidStringType, Object> {
  private String fieldName;

  @Override
  public void initialize(ValidStringType annotation) {
    this.fieldName = annotation.fieldName();
  }

  @Override
  public boolean isValid(Object Value, ConstraintValidatorContext context) {
    String strValue = (String) Value;
    if (strValue == null) {
      return true;
    }
    if (!(strValue instanceof String)
        || strValue.equalsIgnoreCase("true")
        || strValue.equalsIgnoreCase("false")
        || isNumeric(strValue)) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(fieldName + " can only be a String")
          .addConstraintViolation();
      return false;
    }

    return true;
  }

  private boolean isNumeric(String str) {
    try {
      Double.parseDouble(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}

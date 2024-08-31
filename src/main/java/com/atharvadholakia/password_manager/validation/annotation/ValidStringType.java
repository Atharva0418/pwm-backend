package com.atharvadholakia.password_manager.validation.annotation;

import com.atharvadholakia.password_manager.validation.StringTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = StringTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStringType {
  String message() default "{field} can only be a string";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String fieldName();
}

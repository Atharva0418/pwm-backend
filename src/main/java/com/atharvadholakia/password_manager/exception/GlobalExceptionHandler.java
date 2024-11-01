package com.atharvadholakia.password_manager.exception;

import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<HashMap<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {

    Object invalidObject = ex.getBindingResult().getTarget();
    log.info("Invalid input: {}", invalidObject);
    HashMap<String, StringBuilder> collectAllmessages = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String message = error.getDefaultMessage();

              collectAllmessages
                  .computeIfAbsent(fieldName, k -> new StringBuilder())
                  .append(message);
            });

    HashMap<String, String> response = new HashMap<>();
    collectAllmessages.forEach((field, message) -> response.put(field, message.toString().trim()));

    log.warn("Validation error : {}", response);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<HashMap<String, String>> handleResourceNotFoundException(
      ResourceNotFoundException ex) {
    HashMap<String, String> response = new HashMap<>();
    response.put("error", ex.getMessage());

    log.warn("Resource not found: {}", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<HashMap<String, String>> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException ex) {
    HashMap<String, String> response = new HashMap<>();
    response.put("error", "Inputs can only be strings");

    log.warn("Invalid input: Inputs can only be strings");
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<HashMap<String, String>> handleEmailAlreadyExistsException(
      EmailAlreadyExistsException ex) {
    HashMap<String, String> response = new HashMap<>();
    response.put("email", ex.getMessage());
    log.error(ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<HashMap<String, String>> handleMissingServletRequestParamterException(
      MissingServletRequestParameterException ex) {
    HashMap<String, String> response = new HashMap<>();
    response.put("error", ex.getParameterName() + " parameter is missing.");

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<HashMap<String, String>> handleGenericException(Exception ex) {
    HashMap<String, String> response = new HashMap<>();
    response.put(
        "error",
        "We're sorry, but something went wrong on our end. Please try again later. If the"
            + " problem persists, please contact support.");

    log.error("Unexpected error : ", ex);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

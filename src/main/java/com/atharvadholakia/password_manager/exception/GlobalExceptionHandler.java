package com.atharvadholakia.password_manager.exception;

import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<HashMap<String, String>> MethodArgsNotValidExceptionHandler(
      MethodArgumentNotValidException ex) {

    HashMap<String, String> response = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String FieldName = ((FieldError) error).getField();
              String message = error.getDefaultMessage();
              response.put(FieldName, message);
            });

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<HashMap<String, String>> ResourceNotFoundExceptionHandler(
      ResourceNotFoundException ex) {
    HashMap<String, String> response = new HashMap<>();
    response.put("error", ex.getMessage());

    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<HashMap<String, String>> GenericExceptionHandler(Exception ex) {
    HashMap<String, String> response = new HashMap<>();
    response.put("error", "an unexpected error occurred");

    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

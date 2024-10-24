package com.atharvadholakia.password_manager.exception;

public class EmailAlreadyExistsException extends IllegalArgumentException {
  public EmailAlreadyExistsException(String message) {
    super(message);
  }
}

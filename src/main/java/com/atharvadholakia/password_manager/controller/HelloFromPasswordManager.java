package com.atharvadholakia.password_manager.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloFromPasswordManager {

  @RequestMapping(
      value = "/",
      method = {RequestMethod.GET, RequestMethod.HEAD})
  public String sayHello() {
    return "Hello from Password Manager!";
  }

  @RequestMapping(value = "/health", method = RequestMethod.HEAD)
  public ResponseEntity<Void> healthCheck() {
    log.info("Server is running.");
    return new ResponseEntity<>(HttpStatus.OK);
  }
}

package com.atharvadholakia.password_manager.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping("/health")
  public String healthCheck() {
    log.info("Server is active.");
    return "Server is running.";
  }
}

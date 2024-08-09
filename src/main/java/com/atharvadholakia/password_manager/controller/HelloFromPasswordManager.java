package com.atharvadholakia.password_manager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloFromPasswordManager {

  @GetMapping("/sayhello")
  public String sayHello() {
    return "Hello from Password Manager!";
  }
}

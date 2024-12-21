package com.atharvadholakia.password_manager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloFromPasswordManager {

  @RequestMapping(
      value = "/",
      method = {RequestMethod.GET, RequestMethod.HEAD})
  public String sayHello() {
    return "Hello from Password Manager!";
  }
}

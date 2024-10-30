package com.atharvadholakia.password_manager.controller;

import com.atharvadholakia.password_manager.data.User;
import com.atharvadholakia.password_manager.service.UserService;
import jakarta.validation.Valid;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
@Slf4j
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<HashMap<String, String>> registerUser(@Valid @RequestBody User user) {
    log.info(
        "Registering a user with Email: {} ,Hashed password: {}, Salt: {}",
        user.getEmail(),
        user.getHashedPassword(),
        user.getSalt());
    User registeredUser = userService.registerUser(user);

    HashMap<String, String> response = new HashMap<>();
    response.put("ID", registeredUser.getID());
    response.put("Email", registeredUser.getEmail());

    log.info(
        "Successfully registered a user with ID: {}, Email: {}, Hashed password: {}, Salt: {}",
        registeredUser.getID(),
        registeredUser.getEmail(),
        registeredUser.getHashedPassword(),
        registeredUser.getSalt());
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}

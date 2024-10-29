package com.atharvadholakia.password_manager.controller;

import com.atharvadholakia.password_manager.data.User;
import com.atharvadholakia.password_manager.service.UserService;
import jakarta.validation.Valid;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
    log.info(
        "Registering a user with Email: {} ,Hashed password: {}, Salt: {}",
        user.getEmail(),
        user.getHashedPassword(),
        user.getSalt());
    User registeredUser = userService.registerUser(user);

    log.info(
        "Successfully registered a user with Email: {}, Hashed password: {}, Salt: {}",
        registeredUser.getEmail(),
        registeredUser.getHashedPassword(),
        registeredUser.getSalt());
    return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
  }

  @GetMapping("/salt")
  public ResponseEntity<HashMap<String, String>> getSaltByEmail(@RequestParam String email) {
    log.info("Fetching salt by Email: {}", email);
    String salt = userService.getSaltByEmail(email);

    HashMap<String, String> response = new HashMap<>();

    response.put("Salt", salt);

    log.info("Successfully fetched salt by Email: {}", email);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}

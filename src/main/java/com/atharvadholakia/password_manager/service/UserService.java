package com.atharvadholakia.password_manager.service;

import com.atharvadholakia.password_manager.data.User;
import com.atharvadholakia.password_manager.exception.EmailAlreadyExistsException;
import com.atharvadholakia.password_manager.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User registerUser(User user) {
    log.info("Calling repository from service to register a user.");
    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
      throw new EmailAlreadyExistsException("Email already exists.");
    }
    User newUser = new User(user.getEmail(), user.getHashedPassword(), user.getSalt());

    log.debug("Exiting registerUser from service.");
    return userRepository.save(newUser);
  }
}

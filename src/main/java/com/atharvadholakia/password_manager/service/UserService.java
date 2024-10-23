package com.atharvadholakia.password_manager.service;

import com.atharvadholakia.password_manager.data.User;
import com.atharvadholakia.password_manager.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User registerUser(User user) {
    User newUser = new User(user.getUsername(), user.getHashedPassword(), user.getSalt());

    return userRepository.save(newUser);
  }
}

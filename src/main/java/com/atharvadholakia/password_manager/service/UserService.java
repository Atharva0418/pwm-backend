package com.atharvadholakia.password_manager.service;

import com.atharvadholakia.password_manager.data.User;
import com.atharvadholakia.password_manager.exception.EmailAlreadyExistsException;
import com.atharvadholakia.password_manager.exception.ResourceNotFoundException;
import com.atharvadholakia.password_manager.repository.CredentialRepository;
import com.atharvadholakia.password_manager.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

  private final UserRepository userRepository;

  private final CredentialRepository credentialRepository;

  public UserService(UserRepository userRepository, CredentialRepository credenitalRepository) {
    this.userRepository = userRepository;
    this.credentialRepository = credenitalRepository;
  }

  public User registerUser(User user) {
    log.info("Calling repository from service to register a user.");

    Optional<User> existingUserOptional = userRepository.findByEmail(user.getEmail());

    if (existingUserOptional.isPresent()) {
      User existingUser = existingUserOptional.get();

      if (existingUser.getIsDeleted()) {

        existingUser.setIsDeleted(false);
        existingUser.setHashedPassword(user.getHashedPassword());
        existingUser.setSalt(user.getSalt());
        return userRepository.save(existingUser);
      }

      throw new EmailAlreadyExistsException("Email already exists.");
    }

    User newUser = new User(user.getEmail(), user.getHashedPassword(), user.getSalt());

    log.debug("Exiting registerUser from service.");
    return userRepository.save(newUser);
  }

  public User getUserByEmail(String email) {
    User user =
        userRepository
            .findByEmail(email)
            .orElseThrow(
                () -> new ResourceNotFoundException("User not found with Email: " + email));

    return user;
  }

  public User getUserById(String id) {
    User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

    return user;
  }

  public String getSaltByEmail(String email) {
    log.info("Calling getUserByEmail from service to find user with Email: {}.", email);
    User user = getUserByEmail(email);

    log.debug("Exiting getSaltByEmail from service.");
    return user.getSalt();
  }

  public boolean authenticateLogin(String email, String receivedHashedPassword) {
    log.info("Calling getUserByEmail from service to find user.");
    User user = getUserByEmail(email);

    log.debug("Exiting authenticateLogin from service.");
    return user.getHashedPassword().equals(receivedHashedPassword);
  }

  @Transactional
  public void softDeleteUserById(String id) {
    log.info("Calling getUserById from service to find user with id: {}.", id);
    User user = getUserById(id);

    if (user.getIsDeleted()) {
      throw new ResourceNotFoundException("User not found with id: " + id);
    }

    log.info("Calling repository to soft delete the user by id: {}", id);
    userRepository.softDeleteUserById(id);
    credentialRepository.deleteAllCredentialsByEmail(user.getEmail());
  }
}

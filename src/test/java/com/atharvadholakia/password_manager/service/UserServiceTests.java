package com.atharvadholakia.password_manager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.atharvadholakia.password_manager.data.User;
import com.atharvadholakia.password_manager.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceTests {

  @Mock private UserRepository userRepository;

  @InjectMocks private UserService userService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testRegisterUser() throws Exception {

    User user =
        new User(
            "testemail@gmail.com",
            "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd895fcec1c812c24d8",
            "E9xRVzI4T3Q1Yk1XUnlLWQ==");

    when(userRepository.save(any(User.class))).thenReturn(user);

    User result = userService.registerUser(user);

    assertEquals(user.getEmail(), result.getEmail());
    assertEquals(user.getHashedPassword(), result.getHashedPassword());
    assertEquals(user.getSalt(), result.getSalt());

    verify(userRepository).save(any(User.class));
  }

  @Test
  public void testGetSaltByEmail() throws Exception {
    User user =
        new User(
            "testemail@gmail.com",
            "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd895fcec1c812c24d8",
            "E9xRVzI4T3Q1Yk1XUnlLWQ==");

    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

    String salt = userService.getSaltByEmail(user.getEmail());

    assertEquals(salt, user.getSalt());
  }

  @Test
  public void testAuthenticateLogin() throws Exception {
    User user =
        new User(
            "testemail@gmail.com",
            "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd895fcec1c812c24d8",
            "E9xRVzI4T3Q1Yk1XUnlLWQ==");

    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

    boolean isAuthenticated =
        userService.authenticateLogin(user.getEmail(), user.getHashedPassword());

    assertTrue(isAuthenticated);
  }
}

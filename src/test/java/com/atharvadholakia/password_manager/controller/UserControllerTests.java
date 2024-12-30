package com.atharvadholakia.password_manager.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.atharvadholakia.password_manager.data.User;
import com.atharvadholakia.password_manager.exception.EmailAlreadyExistsException;
import com.atharvadholakia.password_manager.exception.ResourceNotFoundException;
import com.atharvadholakia.password_manager.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserControllerTests {

  @Autowired private MockMvc mockmvc;

  @MockBean private UserService userService;

  private String createJsonInput(Object email, Object hashedPassword, Object salt) {
    return String.format(
        "{\"email\" : %s , \"hashedPassword\" : %s , \"salt\" : %s }",
        convertToJsonValue(email), convertToJsonValue(hashedPassword), convertToJsonValue(salt));
  }

  private String convertToJsonValue(Object value) {
    if (value == null) {
      return "null";
    } else if (value instanceof String) {
      return String.format("\"%s\"", value);
    } else {
      return value.toString();
    }
  }

  @Test
  public void testRegisterUser() throws Exception {
    User user =
        new User(
            "testemail@gmail.com",
            "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd895fcec1c812c24d8",
            "E9xRVzI4T3Q1Yk1XUnlLWQ==");
    when(userService.registerUser(any(User.class))).thenReturn(user);

    mockmvc
        .perform(
            post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    createJsonInput(user.getEmail(), user.getHashedPassword(), user.getSalt())))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.Email").value(user.getEmail()))
        .andExpect(jsonPath("$.Id").value(user.getId()));

    verify(userService).registerUser(any(User.class));
  }

  @Test
  public void testRegisterUser_NullInputs() throws Exception {
    String nullInputs = createJsonInput(null, null, null);

    mockmvc
        .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(nullInputs))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.email").value("Email cannot be empty!"))
        .andExpect(jsonPath("$.hashedPassword").value("Hashed password cannot be empty!"))
        .andExpect(jsonPath("$.salt").value("Salt cannot be empty!"));
  }

  @Test
  public void testRegisterUser_EmailAlreadyExists() throws Exception {
    User user2 =
        new User(
            "testemail@gmail.com",
            "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd895fcec1c812c24d8",
            "E9xRVzI4T3Q1Yk1XUnlLWQ==");

    when(userService.registerUser(any(User.class)))
        .thenThrow(new EmailAlreadyExistsException("Email already exists."));

    mockmvc
        .perform(
            post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    createJsonInput(user2.getEmail(), user2.getHashedPassword(), user2.getSalt())))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.email").value("Email already exists."));

    verify(userService).registerUser(any(User.class));
  }

  @Test
  public void testGetSaltByEmail() throws Exception {
    User user =
        new User(
            "testemail@gmail.com",
            "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd895fcec1c812c24d8",
            "E9xRVzI4T3Q1Yk1XUnlLWQ==");

    when(userService.getSaltByEmail(user.getEmail())).thenReturn(user.getSalt());

    mockmvc
        .perform(get("/api/salt").param("email", "testemail@gmail.com"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.Salt").value("E9xRVzI4T3Q1Yk1XUnlLWQ=="));

    verify(userService).getSaltByEmail(user.getEmail());
  }

  @Test
  public void testGetSaltByEmail_InvalidReqestParam() throws Exception {
    mockmvc
        .perform(get("/api/salt").param("notemail", "abcde1234"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error").value("email parameter is missing."));
  }

  @Test
  public void testGetSaltByEmail_NotFound() throws Exception {
    String email = "notfound@gmail.com";
    when(userService.getSaltByEmail(email))
        .thenThrow(new ResourceNotFoundException("User not found with Email: " + email));

    mockmvc
        .perform(get("/api/salt").param("email", email))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("User not found with Email: " + email));

    verify(userService).getSaltByEmail(email);
  }

  @Test
  public void testLoginRequest() throws Exception {
    String email = "testemail@gmail.com";
    String hashedPassword = "e884898da28047151d0e56f8dc62927736d6aabbdd895fcec1c812c24d8";

    when(userService.authenticateLogin(email, hashedPassword)).thenReturn(true);

    mockmvc
        .perform(
            post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    String.format(
                        "{\"email\" : \"%s\" , \"hashedPassword\" : \"%s\"}",
                        email, hashedPassword)))
        .andExpect(status().isOk())
        .andExpect(content().string("Login successful"));

    verify(userService).authenticateLogin(email, hashedPassword);
  }

  @Test
  public void testLoginRequest_InvalidPassword() throws Exception {
    when(userService.authenticateLogin(
            "testemail@gmail.com", "e884898da28047151d0e56f8dc62927736d6aabbdd895fcec1c812c24d8"))
        .thenReturn(false);

    mockmvc
        .perform(
            post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    String.format(
                        "{\"email\" : \"%s\" , \"hashedPassword\" : \"%s\"}",
                        "testemail@gmail.com",
                        "e884898da28047151d0e56f8dc62927736d6aabbdd895fcec1c812c24d8")))
        .andExpect(status().isUnauthorized())
        .andExpect(content().string("Invalid Password"));

    verify(userService)
        .authenticateLogin(
            "testemail@gmail.com", "e884898da28047151d0e56f8dc62927736d6aabbdd895fcec1c812c24d8");
  }

  @Test
  public void testLoginRequest_EmailNotFound() throws Exception {
    String email = "testemadil@gmail.com";
    String hashedPassword = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd895fcec1c812c24d8";
    when(userService.authenticateLogin(email, hashedPassword))
        .thenThrow(new ResourceNotFoundException("User not found with Email: " + email));

    mockmvc
        .perform(
            post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    String.format(
                        "{\"email\" : \"%s\" , \"hashedPassword\" : \"%s\"}",
                        email, hashedPassword)))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("User not found with Email: " + email));

    verify(userService).authenticateLogin(email, hashedPassword);
  }
}

package com.atharvadholakia.password_manager.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.atharvadholakia.password_manager.data.User;
import com.atharvadholakia.password_manager.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
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
        .andExpect(jsonPath("$.email").value(user.getEmail()))
        .andExpect(jsonPath("$.hashedPassword").value(user.getHashedPassword()))
        .andExpect(jsonPath("$.salt").value(user.getSalt()));

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
  public void testRegisterUser_InvalidInputs() throws Exception {
    String invalidInputs = createJsonInput(1234, true, false);

    mockmvc
        .perform(
            post("/api/register").contentType(MediaType.APPLICATION_JSON).content(invalidInputs))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.email").value("Email should be valid."))
        .andExpect(
            jsonPath("$.hashedPassword")
                .value(containsString("Hashed password can only be a string.")))
        .andExpect(jsonPath("$.salt").value(containsString("Salt can only be a string.")));
  }

  @Test
  public void testRegisteruser_LessSizeInputs() throws Exception {
    String lessSizeInputs = createJsonInput("abc", "abc", "abc");

    mockmvc
        .perform(
            post("/api/register").contentType(MediaType.APPLICATION_JSON).content(lessSizeInputs))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.email").value("Email should be valid."))
        .andExpect(
            jsonPath("$.hashedPassword")
                .value(containsString("Hashed password must be between 60 and 255 characters.")))
        .andExpect(
            jsonPath("$.salt").value(containsString("Salt must be between 16 and 64 characters.")));
  }

  @Test
  public void testRegisterUser_LargeSizeInputs() throws Exception {
    String largeSizeInputs =
        createJsonInput(
            "abc",
            RandomStringUtils.randomAlphanumeric(256),
            RandomStringUtils.randomAlphanumeric(70));

    mockmvc
        .perform(
            post("/api/register").contentType(MediaType.APPLICATION_JSON).content(largeSizeInputs))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.email").value("Email should be valid."))
        .andExpect(
            jsonPath("$.hashedPassword")
                .value(containsString("Hashed password must be between 60 and 255 characters.")))
        .andExpect(
            jsonPath("$.salt").value(containsString("Salt must be between 16 and 64 characters.")));
  }
}

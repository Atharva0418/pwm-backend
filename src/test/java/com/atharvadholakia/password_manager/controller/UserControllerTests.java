package com.atharvadholakia.password_manager.controller;

import com.atharvadholakia.password_manager.data.User;
import com.atharvadholakia.password_manager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserControllerTests {

  @Autowired private MockMvc mockmvc;

  @MockBean private UserRepository userRepository;
}

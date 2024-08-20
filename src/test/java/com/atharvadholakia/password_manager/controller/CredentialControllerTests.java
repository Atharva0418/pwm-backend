package com.atharvadholakia.password_manager.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atharvadholakia.password_manager.data.Credential;
import com.atharvadholakia.password_manager.service.CredentialService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CredentialController.class)
public class CredentialControllerTests {

  @Autowired private MockMvc mockMvc;

  @MockBean private CredentialService credentialService;

  private Credential credential;

  @BeforeEach
  public void setup() {
    credential = new Credential("TestServiceName", "TestUsername", "TestPassword");
  }

  @Test
  public void testCreateCredential() throws Exception {
    when(credentialService.createCredential("TestServiceName", "TestUsername", "TestPassword"))
        .thenReturn(credential);

    mockMvc
        .perform(
            post("http://localhost:3000/api/credentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"serviceName\":\"TestServiceName\", \"username\":\"TestUsername\","
                        + " \"password\":\"TestPassword\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.serviceName").value("TestServiceName"))
        .andExpect(jsonPath("$.username").value("TestUsername"))
        .andExpect(jsonPath("$.password").value("TestPassword"));

    verify(credentialService).createCredential("TestServiceName", "TestUsername", "TestPassword");
  }

  @Test
  public void testGetCredentialById() throws Exception {
    String id = credential.getId();
    when(credentialService.getCredentialById(id)).thenReturn(credential);

    mockMvc
        .perform(
            get("http://localhost:3000/api/credentials/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.serviceName").value("TestServiceName"))
        .andExpect(jsonPath("$.username").value("TestUsername"))
        .andExpect(jsonPath("$.password").value("TestPassword"));

    verify(credentialService).getCredentialById(id);
  }

  @Test
  public void testGetAllCredentials() throws Exception {
    Credential credential2 = new Credential("TestServiceName2", "TestUsername2", "TestPassword2");
    List<Credential> credentials = Arrays.asList(credential, credential2);

    when(credentialService.getAllCredential()).thenReturn(credentials);

    mockMvc
        .perform(get("http://localhost:3000/api/credentials").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(credential.getId()))
        .andExpect(jsonPath("$[0].serviceName").value("TestServiceName"))
        .andExpect(jsonPath("[0].username").value("TestUsername"))
        .andExpect(jsonPath("[0].password").value("TestPassword"))
        .andExpect(jsonPath("$[1].id").value(credential2.getId()))
        .andExpect(jsonPath("$[1].serviceName").value("TestServiceName2"))
        .andExpect(jsonPath("$[1].username").value("TestUsername2"))
        .andExpect(jsonPath("$[1].password").value("TestPassword2"));

    verify(credentialService).getAllCredential();
  }
}

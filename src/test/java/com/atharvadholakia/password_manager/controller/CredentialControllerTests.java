package com.atharvadholakia.password_manager.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atharvadholakia.password_manager.data.Credential;
import com.atharvadholakia.password_manager.service.CredentialService;
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

  @Test
  public void testCreateCredential() throws Exception {
    Credential credential = new Credential("TestServiceName", "TestUsername", "TestPassword");
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
    Credential credential = new Credential("TestServiceName", "TestUsername", "TestPassword");
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

    verify(credentialService).getCredentialById((id));
  }

  //   @Test
  //   public void testGetAllCredentials() throws Exception {

  //   }
}

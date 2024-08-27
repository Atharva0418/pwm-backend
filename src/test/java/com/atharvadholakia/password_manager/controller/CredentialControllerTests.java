package com.atharvadholakia.password_manager.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atharvadholakia.password_manager.data.Credential;
import com.atharvadholakia.password_manager.exception.ResourceNotFoundException;
import com.atharvadholakia.password_manager.service.CredentialService;
import java.util.Arrays;
import java.util.Collections;
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
    credential = new Credential("TestserviceName", "Testusername", "Testpassword");
  }

  @Test
  public void testCreateCredential() throws Exception {
    when(credentialService.createCredential("TestserviceName", "Testusername", "Testpassword"))
        .thenReturn(credential);

    mockMvc
        .perform(
            post("/api/credentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"serviceName\":\"TestserviceName\", \"username\":\"Testusername\","
                        + " \"password\":\"Testpassword\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.serviceName").value("TestserviceName"))
        .andExpect(jsonPath("$.username").value("Testusername"))
        .andExpect(jsonPath("$.password").value("Testpassword"));

    verify(credentialService).createCredential("TestserviceName", "Testusername", "Testpassword");
  }

  @Test
  public void testCreateCredential_NullInputs() throws Exception {
    String nullInputs = "{\"serviceName\" : null , \"usernamame\" : null, \"password\" : null}";

    mockMvc
        .perform(
            post("/api/credentials").contentType(MediaType.APPLICATION_JSON).content(nullInputs))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.serviceName").exists())
        .andExpect(jsonPath("$.username").exists())
        .andExpect(jsonPath("$.password").exists())
        .andExpect(jsonPath("$.serviceName").value("serviceName cannot be empty!"))
        .andExpect(jsonPath("$.username").value("username cannot be empty!"))
        .andExpect(jsonPath("$.password").value("password cannot be empty!"));
  }

  @Test
  public void testCreateCredential_InvalidInputs() throws Exception {
    String invalidInputs = "{\"serviceName\" : true , \"username\" : 1234, \"password\" : false}";

    mockMvc
        .perform(
            post("/api/credentials").contentType(MediaType.APPLICATION_JSON).content(invalidInputs))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.serviceName").exists())
        .andExpect(jsonPath("$.username").exists())
        .andExpect(jsonPath("$.password").exists())
        .andExpect(jsonPath("$.serviceName").value("serviceName can only be a string"))
        .andExpect(jsonPath("$.username").value("username can only be a string"))
        .andExpect(jsonPath("$.password").value("password can only be a string"));
  }

  @Test
  public void testGetCredentialById() throws Exception {
    String id = credential.getId();
    when(credentialService.getCredentialById(id)).thenReturn(credential);

    mockMvc
        .perform(get("/api/credentials/{id}", id).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.serviceName").value("TestserviceName"))
        .andExpect(jsonPath("$.username").value("Testusername"))
        .andExpect(jsonPath("$.password").value("Testpassword"));

    verify(credentialService).getCredentialById(id);
  }

  @Test
  public void testGetCredentialById_NotFound() throws Exception {
    String nonexistentId = "nonexistentId";
    when(credentialService.getCredentialById(nonexistentId))
        .thenThrow(new ResourceNotFoundException("Credential not found with ID" + nonexistentId));

    mockMvc
        .perform(get("/api/credentials/{id}", nonexistentId))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("Credential not found with ID" + nonexistentId));
  }

  @Test
  public void testGetAllCredentials() throws Exception {
    Credential credential2 = new Credential("TestserviceName2", "Testusername2", "Testpassword2");
    List<Credential> credentials = Arrays.asList(credential, credential2);

    when(credentialService.getAllCredentials()).thenReturn(credentials);

    mockMvc
        .perform(get("/api/credentials").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].id").value(credential.getId()))
        .andExpect(jsonPath("$[0].serviceName").value("TestserviceName"))
        .andExpect(jsonPath("[0].username").value("Testusername"))
        .andExpect(jsonPath("[0].password").value("Testpassword"))
        .andExpect(jsonPath("$[1].id").value(credential2.getId()))
        .andExpect(jsonPath("$[1].serviceName").value("TestserviceName2"))
        .andExpect(jsonPath("$[1].username").value("Testusername2"))
        .andExpect(jsonPath("$[1].password").value("Testpassword2"));

    verify(credentialService).getAllCredentials();
  }

  @Test
  public void testGetAllCredentials_Empty() throws Exception {
    when(credentialService.getAllCredentials()).thenReturn(Collections.emptyList());

    mockMvc
        .perform(get("/api/credentials"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isEmpty());
  }
}

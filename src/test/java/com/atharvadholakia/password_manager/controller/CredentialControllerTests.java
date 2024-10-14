package com.atharvadholakia.password_manager.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atharvadholakia.password_manager.data.Credential;
import com.atharvadholakia.password_manager.exception.ResourceNotFoundException;
import com.atharvadholakia.password_manager.service.CredentialService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
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

  private String createJsonInput(Object serviceName, Object username, Object password) {
    return String.format(
        "{\"serviceName\" : %s , \"username\" : %s , \"password\" : %s }",
        convertToJsonValue(serviceName),
        convertToJsonValue(username),
        convertToJsonValue(password));
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
  public void testCreateCredential() throws Exception {
    Credential credential = new Credential("TestserviceName", "Testusername", "Testpassword@1");
    when(credentialService.createCredential("TestserviceName", "Testusername", "Testpassword@1"))
        .thenReturn(credential);

    mockMvc
        .perform(
            post("/api/credentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJsonInput("TestserviceName", "Testusername", "Testpassword@1")))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.serviceName").value("TestserviceName"))
        .andExpect(jsonPath("$.username").value("Testusername"))
        .andExpect(jsonPath("$.password").value("Testpassword@1"));

    verify(credentialService).createCredential("TestserviceName", "Testusername", "Testpassword@1");
  }

  @Test
  public void testCreateCredential_NullInputs() throws Exception {
    String nullInputs = createJsonInput(null, null, null);

    mockMvc
        .perform(
            post("/api/credentials").contentType(MediaType.APPLICATION_JSON).content(nullInputs))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.serviceName").value("Servicename cannot be empty!"))
        .andExpect(jsonPath("$.username").value("Username cannot be empty!"))
        .andExpect(jsonPath("$.password").value("Password cannot be empty!"));
  }

  @Test
  public void testCreateCredential_InvalidInputs() throws Exception {
    String invalidInputs = createJsonInput(1234, true, false);

    mockMvc
        .perform(
            post("/api/credentials").contentType(MediaType.APPLICATION_JSON).content(invalidInputs))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.serviceName").value("Servicename can only be a string."))
        .andExpect(jsonPath("$.username").value("Username can only be a string."))
        .andExpect(jsonPath("$.password").value(containsString("Password can only be a string.")))
        .andExpect(
            jsonPath("$.password")
                .value(containsString("Password must be between 8 to 25 characters.")))
        .andExpect(
            jsonPath("$.password")
                .value(
                    containsString(
                        "Password must include 1 uppercase, 1 lowercase, 1 digit, 1 special"
                            + " character and no spaces.")));
  }

  @Test
  public void testCreateCredential_LessSizeInput() throws Exception {
    String lessSizeInput = createJsonInput("$N", "uN", "pwrd");

    mockMvc
        .perform(
            post("/api/credentials").contentType(MediaType.APPLICATION_JSON).content(lessSizeInput))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.serviceName")
                .value(containsString("Servicename must be between 3 to 25 characters.")))
        .andExpect(
            jsonPath("$.serviceName")
                .value(
                    containsString(
                        "Invalid charactes. Only alphanumerics, dots, underscores, hyphens and"
                            + " spaces are allowed.")))
        .andExpect(
            jsonPath("$.username")
                .value(containsString("Username must be between 3 to 25 characters.")))
        .andExpect(
            jsonPath("$.password")
                .value(containsString("Password must be between 8 to 25 characters.")))
        .andExpect(
            jsonPath("$.password")
                .value(
                    containsString(
                        "Password must include 1 uppercase, 1 lowercase, 1 digit, 1 special"
                            + " character and no spaces.")));
  }

  @Test
  public void testCreateCredential_LargeSizeInput() throws Exception {
    String largeSizeInput =
        createJsonInput(
            RandomStringUtils.randomAlphanumeric(30),
            RandomStringUtils.randomAlphanumeric(30),
            RandomStringUtils.randomAlphanumeric(30));

    mockMvc
        .perform(
            post("/api/credentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(largeSizeInput))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.serviceName").value("Servicename must be between 3 to 25 characters."))
        .andExpect(jsonPath("$.username").value("Username must be between 3 to 25 characters."))
        .andExpect(
            jsonPath("$.password")
                .value(containsString("Password must be between 8 to 25 characters.")))
        .andExpect(
            jsonPath("$.password")
                .value(
                    containsString(
                        "Password must include 1 uppercase, 1 lowercase, 1 digit, 1 special"
                            + " character and no spaces.")));
  }

  @Test
  public void testCreateCredential_InvalidCharInput() throws Exception {
    String invalidCharInput = createJsonInput("$ervicename", "user name", "Testpassword");

    mockMvc
        .perform(
            post("/api/credentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidCharInput))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.serviceName").exists())
        .andExpect(jsonPath("$.username").exists())
        .andExpect(jsonPath("$.password").exists())
        .andExpect(
            jsonPath("$.serviceName")
                .value(
                    "Invalid charactes. Only alphanumerics, dots, underscores, hyphens and spaces"
                        + " are allowed."))
        .andExpect(
            jsonPath("$.username")
                .value(
                    "Invalid characters. Only alphanumerics, dots, underscores and hyphens are"
                        + " allowed."))
        .andExpect(
            jsonPath("$.password")
                .value(
                    "Password must include 1 uppercase, 1 lowercase, 1 digit, 1 special character"
                        + " and no spaces."));
  }

  @Test
  public void testGetCredentialById() throws Exception {
    Credential credential = new Credential("TestserviceName", "Testusername", "Testpassword@1");
    String id = credential.getId();
    when(credentialService.getCredentialById(id)).thenReturn(credential);

    mockMvc
        .perform(get("/api/credentials/{id}", id).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.serviceName").value("TestserviceName"))
        .andExpect(jsonPath("$.username").value("Testusername"))
        .andExpect(jsonPath("$.password").value("Testpassword@1"));

    verify(credentialService).getCredentialById(id);
  }

  @Test
  public void testGetCredentialById_NotFound() throws Exception {
    String nonexistentId = "nonexistentId";
    when(credentialService.getCredentialById(nonexistentId))
        .thenThrow(new ResourceNotFoundException("Credential not found with ID " + nonexistentId));

    mockMvc
        .perform(get("/api/credentials/{id}", nonexistentId))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("Credential not found with ID " + nonexistentId));
  }

  @Test
  public void testGetAllCredentials() throws Exception {
    Credential credential1 = new Credential("TestserviceName1", "Testusername1", "Testpassword@11");
    Credential credential2 = new Credential("TestserviceName2", "Testusername2", "Testpassword@12");
    List<Credential> credentials = Arrays.asList(credential1, credential2);

    when(credentialService.getAllCredentials()).thenReturn(credentials);

    mockMvc
        .perform(get("/api/credentials").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].id").value(credential1.getId()))
        .andExpect(jsonPath("$[0].serviceName").value("TestserviceName1"))
        .andExpect(jsonPath("$[0].username").value("Testusername1"))
        .andExpect(jsonPath("$[0].password").value("Testpassword@11"))
        .andExpect(jsonPath("$[1].id").value(credential2.getId()))
        .andExpect(jsonPath("$[1].serviceName").value("TestserviceName2"))
        .andExpect(jsonPath("$[1].username").value("Testusername2"))
        .andExpect(jsonPath("$[1].password").value("Testpassword@12"));

    verify(credentialService).getAllCredentials();
  }

  @Test
  public void testGetAllCredentials_EmptyList() throws Exception {
    when(credentialService.getAllCredentials()).thenReturn(Collections.emptyList());

    mockMvc
        .perform(get("/api/credentials"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isEmpty());
  }

  @Test
  public void testUpdateCredential() throws Exception {
    String credentialID = "1234";
    Credential existingCredential =
        new Credential("TestServicename1", "TestUsername1", "TestPassword@1");
    existingCredential.setId(credentialID);

    Credential updatedCredential =
        new Credential("UpdatedServiceName", "UpdatedUsername", "UpdatedPassword@1");
    updatedCredential.setId(credentialID);

    when(credentialService.getCredentialById(credentialID)).thenReturn(existingCredential);
    when(credentialService.updateCredential(
            existingCredential, "UpdatedServiceName", "UpdatedUsername", "UpdatedPassword@1"))
        .thenReturn(updatedCredential);

    mockMvc
        .perform(
            patch("/api/credentials/{id}", credentialID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    createJsonInput("UpdatedServiceName", "UpdatedUsername", "UpdatedPassword@1")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(credentialID))
        .andExpect(jsonPath("$.serviceName").value(updatedCredential.getServiceName()))
        .andExpect(jsonPath("$.username").value(updatedCredential.getUsername()))
        .andExpect(jsonPath("$.password").value(updatedCredential.getPassword()));

    verify(credentialService).getCredentialById(credentialID);
    verify(credentialService)
        .updateCredential(
            existingCredential, "UpdatedServiceName", "UpdatedUsername", "UpdatedPassword@1");
  }

  @Test
  public void testUpdateCredential_NotFound() throws Exception {
    String nonexistentID = "1234";
    when(credentialService.getCredentialById(nonexistentID))
        .thenThrow(new ResourceNotFoundException("Credential not found with ID " + nonexistentID));

    mockMvc
        .perform(
            patch("/api/credentials/{id}", nonexistentID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJsonInput("TestServiceName", "TestUsername", "TestPassword@1")))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("Credential not found with ID " + nonexistentID));

    verify(credentialService).getCredentialById(nonexistentID);
  }

  @Test
  public void testUpdateCredential_NullInputs() throws Exception {

    String nullInputs = createJsonInput(null, null, null);

    mockMvc
        .perform(
            patch("/api/credentials/{id}", "ID")
                .contentType(MediaType.APPLICATION_JSON)
                .content(nullInputs))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.serviceName").value("Servicename cannot be empty!"))
        .andExpect(jsonPath("$.username").value("Username cannot be empty!"))
        .andExpect(jsonPath("$.password").value("Password cannot be empty!"));
  }

  @Test
  public void testUpdateCredential_InvalidInputs() throws Exception {
    String invalidInputs = createJsonInput(1234, true, false);

    mockMvc
        .perform(
            patch("/api/credentials/{id}", "ID")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidInputs))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.serviceName").value("Servicename can only be a string."))
        .andExpect(jsonPath("$.username").value("Username can only be a string."))
        .andExpect(jsonPath("$.password").value(containsString("Password can only be a string.")))
        .andExpect(
            jsonPath("$.password")
                .value(containsString("Password must be between 8 to 25 characters.")))
        .andExpect(
            jsonPath("$.password")
                .value(
                    containsString(
                        "Password must include 1 uppercase, 1 lowercase, 1 digit, 1 special"
                            + " character and no spaces.")));
  }

  @Test
  public void testUpdateCredential_LessSizeInput() throws Exception {
    String lessSizeInput = createJsonInput("$N", "uN", "pwrd");

    mockMvc
        .perform(
            patch("/api/credentials/{id}", "ID")
                .contentType(MediaType.APPLICATION_JSON)
                .content(lessSizeInput))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.serviceName")
                .value(containsString("Servicename must be between 3 to 25 characters.")))
        .andExpect(
            jsonPath("$.serviceName")
                .value(
                    containsString(
                        "Invalid charactes. Only alphanumerics, dots, underscores, hyphens and"
                            + " spaces are allowed.")))
        .andExpect(
            jsonPath("$.username")
                .value(containsString("Username must be between 3 to 25 characters.")))
        .andExpect(
            jsonPath("$.password")
                .value(containsString("Password must be between 8 to 25 characters.")))
        .andExpect(
            jsonPath("$.password")
                .value(
                    containsString(
                        "Password must include 1 uppercase, 1 lowercase, 1 digit, 1 special"
                            + " character and no spaces.")));
  }

  @Test
  public void testUpdateCredential_LargeSizeInput() throws Exception {
    String largeSizeInput =
        createJsonInput(
            RandomStringUtils.randomAlphanumeric(30),
            RandomStringUtils.randomAlphanumeric(30),
            RandomStringUtils.randomAlphanumeric(30));

    mockMvc
        .perform(
            patch("/api/credentials/{id}", "ID")
                .contentType(MediaType.APPLICATION_JSON)
                .content(largeSizeInput))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.serviceName").value("Servicename must be between 3 to 25 characters."))
        .andExpect(jsonPath("$.username").value("Username must be between 3 to 25 characters."))
        .andExpect(
            jsonPath("$.password")
                .value(containsString("Password must be between 8 to 25 characters.")))
        .andExpect(
            jsonPath("$.password")
                .value(
                    containsString(
                        "Password must include 1 uppercase, 1 lowercase, 1 digit, 1 special"
                            + " character and no spaces.")));
  }

  @Test
  public void testUpdateCredential_InvalidCharInput() throws Exception {
    String invalidCharInput = createJsonInput("$ervicename", "user name", "Testpassword");

    mockMvc
        .perform(
            post("/api/credentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidCharInput))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.serviceName")
                .value(
                    "Invalid charactes. Only alphanumerics, dots, underscores, hyphens and spaces"
                        + " are allowed."))
        .andExpect(
            jsonPath("$.username")
                .value(
                    "Invalid characters. Only alphanumerics, dots, underscores and hyphens are"
                        + " allowed."))
        .andExpect(
            jsonPath("$.password")
                .value(
                    "Password must include 1 uppercase, 1 lowercase, 1 digit, 1 special character"
                        + " and no spaces."));
  }

  @Test
  public void testDeleteCredentialByID() throws Exception {
    String credentialID = "1234";
    Credential credential = new Credential("TestServicename1", "TestUsername1", "TestPassword@1");
    credential.setId(credentialID);

    when(credentialService.getCredentialById(credentialID)).thenReturn(credential);
    doNothing().when(credentialService).deleteCredentialById(credentialID);

    mockMvc
        .perform(delete("/api/credentials/{id}", credentialID))
        .andExpect(status().isNoContent());

    verify(credentialService).getCredentialById(credentialID);
    verify(credentialService).deleteCredentialById(credentialID);
  }

  @Test
  public void testDeleteCredentialByID_NotFound() throws Exception {
    String nonexistentID = "1234";

    when(credentialService.getCredentialById(nonexistentID))
        .thenThrow(new ResourceNotFoundException("Credential not found with ID " + nonexistentID));

    mockMvc
        .perform(delete("/api/credentials/{id}", nonexistentID))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("Credential not found with ID " + nonexistentID));

    verify(credentialService).getCredentialById(nonexistentID);
  }
}

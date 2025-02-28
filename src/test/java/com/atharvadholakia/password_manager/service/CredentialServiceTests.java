package com.atharvadholakia.password_manager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.atharvadholakia.password_manager.data.Credential;
import com.atharvadholakia.password_manager.data.User;
import com.atharvadholakia.password_manager.exception.ResourceNotFoundException;
import com.atharvadholakia.password_manager.repository.CredentialRepository;
import com.atharvadholakia.password_manager.repository.UserRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CredentialServiceTests {

  @Mock private CredentialRepository credentialRepository;

  @Mock private UserRepository userRepository;

  @InjectMocks private CredentialService credentialService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testCreateCredential() throws Exception {
    User user =
        new User(
            "testemail@gmail.com",
            "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd895fcec1c812c24d8",
            "E9xRVzI4T3Q1Yk1XUnlLWQ==");
    Credential credential = new Credential("TestServiceName", "TestUsername", "TestP@ssword1");
    credential.setUser(user);

    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
    when(credentialRepository.save(credential)).thenReturn(credential);

    Credential result = credentialService.createCredential(user.getEmail(), credential);

    assertEquals(credential.getServiceName(), result.getServiceName());
    assertEquals(credential.getUsername(), result.getUsername());
    assertEquals(credential.getPassword(), result.getPassword());

    verify(userRepository).findByEmail(user.getEmail());
    verify(credentialRepository).save(result);
  }

  @Test
  public void testGetCredentialById() throws Exception {
    Credential credential = new Credential("TestServiceName", "TestUsername", "TestPassword");
    String id = credential.getId();
    when(credentialRepository.findById(id)).thenReturn(Optional.of(credential));

    Optional<Credential> result = credentialRepository.findById(id);

    assertTrue(result.isPresent());
    assertEquals(credential.getServiceName(), result.get().getServiceName());
    assertEquals(credential.getUsername(), result.get().getUsername());
    assertEquals(credential.getPassword(), result.get().getPassword());

    verify(credentialRepository).findById(id);
  }

  @Test
  public void testGetCredentialById_NotFound() throws Exception {
    String id = "invalidId";
    when(credentialRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> credentialService.getCredentialById(id));

    verify(credentialRepository, times(1)).findById(id);
  }

  @Test
  public void testGetAllCredentialsByUserEmail() throws Exception {
    Credential credential1 = new Credential("TestServiceName1", "TestUsername1", "TestPassword1");
    Credential credential2 = new Credential("TestServiceName2", "TestUsername2", "TestPassword2");

    List<Credential> expectedCredentials = Arrays.asList(credential1, credential2);
    when(credentialRepository.findByUserEmail("testemail@gmail.com"))
        .thenReturn(expectedCredentials);

    List<Credential> actualCredentials =
        credentialService.getAllCredentialsByUserEmail("testemail@gmail.com");

    assertEquals(expectedCredentials, actualCredentials);

    verify(credentialRepository).findByUserEmail("testemail@gmail.com");
  }

  @Test
  public void testGetAllCredentials_EmptyList() throws Exception {
    when(credentialRepository.findByUserEmail("testemail@gmail.com"))
        .thenReturn(Collections.emptyList());

    List<Credential> credential =
        credentialService.getAllCredentialsByUserEmail("testemail@gmail.com");

    assertTrue(credential.isEmpty());

    verify(credentialRepository).findByUserEmail("testemail@gmail.com");
  }

  @Test
  public void testUpdateCredential() throws Exception {
    String id = "1234";
    Credential existingCredential =
        new Credential("TestServicename1", "TestUsername1", "TestPassword@1");
    existingCredential.setId(id);

    existingCredential =
        credentialService.updateCredential(
            existingCredential, "UpdatedServiceName", "UpdatedUsername", "UpdatedPassword@1");

    assertEquals(existingCredential.getServiceName(), "UpdatedServiceName");
    assertEquals(existingCredential.getUsername(), "UpdatedUsername");
    assertEquals(existingCredential.getPassword(), "UpdatedPassword@1");

    verify(credentialRepository).save(existingCredential);
  }

  @Test
  public void testDeleteCredentialByID() throws Exception {
    credentialRepository.deleteById("ID");

    verify(credentialRepository).deleteById("ID");
  }
}

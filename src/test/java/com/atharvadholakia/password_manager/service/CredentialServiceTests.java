package com.atharvadholakia.password_manager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.atharvadholakia.password_manager.data.Credential;
import com.atharvadholakia.password_manager.exception.ResourceNotFoundException;
import com.atharvadholakia.password_manager.repository.CredentialRepository;
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

  @InjectMocks private CredentialService credentialService;

  private Credential testCredential;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    testCredential = new Credential("TestServiceName", "TestUsername", "TestPassword");
  }

  @Test
  public void testCreateCredential() throws Exception {

    doNothing().when(credentialRepository).save(any(Credential.class));

    Credential result =
        credentialService.createCredential("TestServiceName", "TestUsername", "TestPassword");

    assertEquals(testCredential.getServiceName(), result.getServiceName());
    assertEquals(testCredential.getUsername(), result.getUsername());
    assertEquals(testCredential.getPassword(), result.getPassword());

    verify(credentialRepository).save(result);
  }

  @Test
  public void testGetCredentialById() throws Exception {
    String id = testCredential.getId();
    when(credentialRepository.findById(id)).thenReturn(Optional.of(testCredential));

    Optional<Credential> result = credentialRepository.findById(id);

    assertTrue(result.isPresent());
    assertEquals(testCredential.getServiceName(), result.get().getServiceName());
    assertEquals(testCredential.getUsername(), result.get().getUsername());
    assertEquals(testCredential.getPassword(), result.get().getPassword());

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
  public void testGetAllCredentials() throws Exception {
    Credential testCredential2 =
        new Credential("TestServiceName1", "TestUsername1", "TestPassword1");

    List<Credential> expectedCredentials = Arrays.asList(testCredential, testCredential2);
    when(credentialRepository.findAll()).thenReturn(expectedCredentials);

    List<Credential> actualCredentials = credentialService.getAllCredentials();

    assertEquals(expectedCredentials, actualCredentials);

    verify(credentialRepository).findAll();
  }

  @Test
  public void testGetAllCredentials_EmptyList() throws Exception {
    when(credentialRepository.findAll()).thenReturn(Collections.emptyList());

    List<Credential> credential = credentialService.getAllCredentials();

    assertTrue(credential.isEmpty());

    verify(credentialRepository).findAll();
  }
}

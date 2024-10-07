package com.atharvadholakia.password_manager.controller;

import com.atharvadholakia.password_manager.data.Credential;
import com.atharvadholakia.password_manager.service.CredentialService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credentials")
@Slf4j
public class CredentialController {

  private final CredentialService credentialService;

  public CredentialController(CredentialService credentialService) {
    this.credentialService = credentialService;
  }

  @PostMapping
  public ResponseEntity<Credential> createCredential(@Valid @RequestBody Credential credential) {
    log.info(
        "Creating a credential with Servicename: {}, Username: {}, Password: {}.",
        credential.getServiceName(),
        credential.getUsername(),
        credential.getPassword());
    Credential createdCredential =
        credentialService.createCredential(
            credential.getServiceName(), credential.getUsername(), credential.getPassword());
    log.info("Successfully created Credential with ID: {}", createdCredential.getId());
    return new ResponseEntity<>(createdCredential, HttpStatus.CREATED);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Credential> updateCredential(
      @PathVariable String id, @Valid @RequestBody Credential credential) {
    Credential oldCredential = credentialService.getCredentialById(id);
    log.info(
        "Updating a credential with ID: {}, Servicename: {}, Username: {}, Password: {}",
        id,
        oldCredential.getServiceName(),
        oldCredential.getUsername(),
        oldCredential.getPassword());
    Credential updatedCredential =
        credentialService.updateCredential(
            id, credential.getServiceName(), credential.getUsername(), credential.getPassword());
    log.info(
        "Successfully updated Credential with ID: {} to Servicename: {}, Username: {}, Password: {}"
            + " ",
        id,
        updatedCredential.getServiceName(),
        updatedCredential.getUsername(),
        updatedCredential.getPassword());
    return new ResponseEntity<>(updatedCredential, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Credential> getCredentialById(@PathVariable String id) {
    log.info("Fetching credential with ID: {}", id);
    Credential credential = credentialService.getCredentialById(id);

    log.info("Found credential with ID: {}", id);

    return new ResponseEntity<>(credential, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Credential>> getAllCredentials() {
    log.info("Fetching all credentials");
    List<Credential> credentials = credentialService.getAllCredentials();
    log.info("Successfully fetched {} credentials", credentials.size());
    return new ResponseEntity<>(credentials, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCredentialById(@PathVariable String id) {
    log.info("Deleting a credential with ID: {}", id);
    credentialService.getCredentialById(id);
    credentialService.deleteCredentialById(id);

    log.info("Successfully deleted credential with ID: {}", id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

package com.atharvadholakia.password_manager.controller;

import com.atharvadholakia.password_manager.data.Credential;
import com.atharvadholakia.password_manager.data.User;
import com.atharvadholakia.password_manager.service.CredentialService;
import com.atharvadholakia.password_manager.service.UserService;
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

  private final UserService userService;

  public CredentialController(CredentialService credentialService, UserService userService) {
    this.credentialService = credentialService;
    this.userService = userService;
  }

  @PostMapping("/{email}")
  public ResponseEntity<Credential> createCredential(
      @PathVariable String email, @Valid @RequestBody Credential credential) {
    User user = userService.getUserByEmail(email);

    credential.setUser(user);
    log.info(
        "Creating a credential with Servicename: {}, Username: {}, Password: {}.",
        credential.getServiceName(),
        credential.getUsername(),
        credential.getPassword());
    Credential createdCredential = credentialService.createCredential(email, credential);
    log.info("Successfully created Credential with ID: {}", createdCredential.getCredentialId());
    return new ResponseEntity<>(createdCredential, HttpStatus.CREATED);
  }

  @PatchMapping("/{credentialId}")
  public ResponseEntity<Credential> updateCredential(
      @PathVariable String credentialId, @Valid @RequestBody Credential credential) {
    Credential existingCredential = credentialService.getCredentialById(credentialId);
    log.info(
        "Updating a credential with ID: {}, Servicename: {}, Username: {}, Password: {}",
        credentialId,
        existingCredential.getServiceName(),
        existingCredential.getUsername(),
        existingCredential.getPassword());
    Credential updatedCredential =
        credentialService.updateCredential(
            existingCredential,
            credential.getServiceName(),
            credential.getUsername(),
            credential.getPassword());
    log.info(
        "Successfully updated Credential with ID: {} to Servicename: {}, Username: {}, Password: {}"
            + " ",
        credentialId,
        updatedCredential.getServiceName(),
        updatedCredential.getUsername(),
        updatedCredential.getPassword());
    return new ResponseEntity<>(updatedCredential, HttpStatus.OK);
  }

  @GetMapping("/{credentialId}")
  public ResponseEntity<Credential> getCredentialById(@PathVariable String credentialId) {
    log.info("Fetching credential with ID: {}", credentialId);
    Credential credential = credentialService.getCredentialById(credentialId);

    log.info("Found credential with ID: {}", credentialId);

    return new ResponseEntity<>(credential, HttpStatus.OK);
  }

  @GetMapping("/user/{email}")
  public ResponseEntity<List<Credential>> getAllCredentialsByUserEmail(@PathVariable String email) {
    log.info("Fetching all credentials");
    List<Credential> credentials = credentialService.getAllCredentialsByUserEmail(email);
    log.info("Successfully fetched {} credentials", credentials.size());
    return new ResponseEntity<>(credentials, HttpStatus.OK);
  }

  @DeleteMapping("/{credentialId}")
  public ResponseEntity<Void> deleteCredentialById(@PathVariable String credentialId) {
    log.info("Deleting a credential with ID: {}", credentialId);
    credentialService.getCredentialById(credentialId);
    credentialService.deleteCredentialById(credentialId);

    log.info("Successfully deleted credential with ID: {}", credentialId);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

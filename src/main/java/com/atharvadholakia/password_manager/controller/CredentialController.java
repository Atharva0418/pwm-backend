package com.atharvadholakia.password_manager.controller;

import com.atharvadholakia.password_manager.data.Credential;
import com.atharvadholakia.password_manager.service.CredentialService;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credentials")
public class CredentialController {

  private static final Logger logger = LoggerFactory.getLogger(CredentialController.class);

  private final CredentialService credentialService;

  public CredentialController(CredentialService credentialService) {
    this.credentialService = credentialService;
  }

  @PostMapping
  public ResponseEntity<Credential> createCredential(@Valid @RequestBody Credential credential) {
    logger.debug(
        "Creating Credential with Servicename: {}, Username: {}, Password: {}.",
        credential.getServiceName(),
        credential.getUsername(),
        credential.getPassword());
    Credential createdCredential =
        credentialService.createCredential(
            credential.getServiceName(), credential.getUsername(), credential.getPassword());
    logger.info(
        "Created Credential with Servicename: {}, Username: {}, Password: {}.",
        credential.getServiceName(),
        credential.getUsername(),
        credential.getPassword());
    return new ResponseEntity<>(createdCredential, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Credential> getCredentialById(@PathVariable String id) {
    Credential credential = credentialService.getCredentialById(id);

    return new ResponseEntity<>(credential, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Credential>> getAllCredentials() {
    List<Credential> credentials = credentialService.getAllCredentials();
    return new ResponseEntity<>(credentials, HttpStatus.OK);
  }
}

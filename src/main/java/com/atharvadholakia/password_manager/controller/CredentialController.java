package com.atharvadholakia.password_manager.controller;

import com.atharvadholakia.password_manager.data.Credential;
import com.atharvadholakia.password_manager.service.CredentialService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credentials")
public class CredentialController {

  private final CredentialService credentialService;

  public CredentialController(CredentialService credentialService) {
    this.credentialService = credentialService;
  }

  @PostMapping
  public ResponseEntity<Credential> createCredential(@Valid @RequestBody Credential credential) {
    Credential createdCredential =
        credentialService.createCredential(
            credential.getServicename(), credential.getUsername(), credential.getPassword());
    return new ResponseEntity<>(createdCredential, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Credential> getCredentialById(@PathVariable String id) {
    Credential credential = credentialService.getCredentialById(id);

    return new ResponseEntity<>(credential, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Credential>> getAllCredentials() {
    List<Credential> credentials = credentialService.getAllCredential();
    return new ResponseEntity<>(credentials, HttpStatus.OK);
  }
}

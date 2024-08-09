package com.atharvadholakia.password_manager.controller;

import com.atharvadholakia.password_manager.data.Credential;
import com.atharvadholakia.password_manager.service.CredentialService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credentials")
public class CredentialController {

  private final CredentialService credentialService;

  @Autowired
  public CredentialController(CredentialService credentialService) {
    this.credentialService = credentialService;
  }

  @PostMapping
  public Credential createCredential(@RequestBody Credential credential) {
    return credentialService.createCredential(
        credential.getServicename(),
        credential.getUsername(),
        credential.getPassword());
  }

  @GetMapping("/{id}")
  public Optional<Credential> getCredentialById(@PathVariable String id) {
    return credentialService.getCredentialById(id);
  }

  @GetMapping
  public List<Credential> getAllCredentials() {
    return credentialService.getAllCredential();
  }
}

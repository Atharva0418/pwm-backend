package com.atharvadholakia.password_manager.service;

import com.atharvadholakia.password_manager.data.Credential;
import com.atharvadholakia.password_manager.exception.ResourceNotFoundException;
import com.atharvadholakia.password_manager.repository.CredentialRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {

  private final CredentialRepository credentialRepository;

  public CredentialService(CredentialRepository credentialRepository) {
    this.credentialRepository = credentialRepository;
  }

  public Credential createCredential(String servicename, String username, String password) {

    Credential credential = new Credential(servicename, username, password);

    credentialRepository.save(credential);

    return credential;
  }

  public Credential getCredentialById(String id) {
    return credentialRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Credential not found with ID " + id));
  }

  public List<Credential> getAllCredential() {
    return credentialRepository.findAll();
  }
}

package com.atharvadholakia.password_manager.service;

import com.atharvadholakia.password_manager.data.Credential;
import com.atharvadholakia.password_manager.exception.EntityNotFoundException;
import com.atharvadholakia.password_manager.repository.CredentialRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {

  private final CredentialRepository credentialRepository;

  @Autowired
  public CredentialService(CredentialRepository credentialRepository) {
    this.credentialRepository = credentialRepository;
  }

  public Credential createCredential(String servicename, String username, String password) {

    if (credentialRepository.findByUsername(username).isPresent()) {
      throw new IllegalArgumentException("Username already exists");
    }

    Credential credential = new Credential(servicename, username, password);

    credentialRepository.save(credential);

    return credential;
  }

  public Optional<Credential> getCredentialById(String id) {
    return credentialRepository.findById(id);
  }


  public List<Credential> getAllCredential() {
    return credentialRepository.findAll();
  }
}

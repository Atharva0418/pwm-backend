package com.atharvadholakia.password_manager.service;

import com.atharvadholakia.password_manager.data.Credential;
import com.atharvadholakia.password_manager.exception.EntityNotFoundException;
import com.atharvadholakia.password_manager.repository.CredentialRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CredentialService {

  private final CredentialRepository credentialRepository;

  @Autowired
  public CredentialService(CredentialRepository credentialRepository) {
    this.credentialRepository = credentialRepository;
  }

  public Credential createCredential(String servicename, String username, String password) {

    Credential credential = new Credential(servicename, username, password);

    if (credential.getServicename() == null || credential.getServicename().trim().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Servicename cannot be empty");
    }

    if (credential.getUsername() == null || credential.getUsername().trim().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username cannot be empty");
    }

    if (credential.getPassword() == null || credential.getPassword().trim().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password cannot be empty");
    }

    if (credentialRepository.findByUsername(username).isPresent()) {
      throw new IllegalArgumentException("Username already exists");
    }

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

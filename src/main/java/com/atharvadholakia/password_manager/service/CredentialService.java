package com.atharvadholakia.password_manager.service;

import com.atharvadholakia.password_manager.data.Credential;
import com.atharvadholakia.password_manager.exception.ResourceNotFoundException;
import com.atharvadholakia.password_manager.repository.CredentialRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CredentialService {

  private final CredentialRepository credentialRepository;

  public CredentialService(CredentialRepository credentialRepository) {
    this.credentialRepository = credentialRepository;
  }

  public Credential createCredential(String serviceName, String username, String password) {
    Credential credential = new Credential(serviceName, username, password);

    log.info("Calling repository from service to write to DB.");
    credentialRepository.save(credential);

    log.debug("Exiting createCredential from service");
    return credential;
  }

  public Credential updateCredential(
      String id, String serviceName, String username, String password) {
    Credential credential = getCredentialById(id);

    if (credential.getUsername() != null) {
      credential.setUsername(username);
    }
    if (credential.getServiceName() != null) {
      credential.setServicename(serviceName);
    }
    if (credential.getPassword() != null) {
      credential.setPassword(password);
    }

    log.debug("Exiting updateCredential from service");
    credentialRepository.save(credential);
    return credential;
  }

  public Credential getCredentialById(String id) {
    log.info("Calling repository from service to get a credential with ID: {}", id);
    return credentialRepository
        .findById(id)
        .orElseThrow(
            () -> {
              return new ResourceNotFoundException("Credential not found with ID " + id);
            });
  }

  public List<Credential> getAllCredentials() {
    log.info("Calling repository to get all the credentials");
    return credentialRepository.findAll();
  }

  public void deleteCredentialById(String id) {
    log.info("Calling repository to delete a credential with ID: {}", id);
    credentialRepository.deleteById(id);
  }
}

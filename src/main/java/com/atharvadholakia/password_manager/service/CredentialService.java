package com.atharvadholakia.password_manager.service;

import com.atharvadholakia.password_manager.data.Credential;
import com.atharvadholakia.password_manager.exception.ResourceNotFoundException;
import com.atharvadholakia.password_manager.repository.CredentialRepositoryJPA;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CredentialService {

  private final CredentialRepositoryJPA credentialRepositoryJPA;

  public CredentialService(CredentialRepositoryJPA credentialRepositoryJPA) {
    this.credentialRepositoryJPA = credentialRepositoryJPA;
  }

  public Credential createCredential(String serviceName, String username, String password) {
    Credential credential = new Credential(serviceName, username, password);

    log.debug("Calling repository from service");
    credentialRepositoryJPA.save(credential);

    log.trace("Exiting createCredential method in service");
    return credential;
  }

  public Credential getCredentialById(String id) {
    log.debug("Calling repository from service");
    return credentialRepositoryJPA
        .findById(id)
        .orElseThrow(
            () -> {
              return new ResourceNotFoundException("Credential not found with ID " + id);
            });
  }

  public List<Credential> getAllCredentials() {
    log.debug("Calling Repository to get all the credentials");
    return credentialRepositoryJPA.findAll();
  }
}

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
    log.trace("Entering createCredential method in service");
    log.info(
        "Attempting to create a credential with Servicename: {}, Username: {}, Password: {}",
        serviceName,
        username,
        password);
    Credential credential = new Credential(serviceName, username, password);

    log.trace("Entering repository to store the credential with ID: {}", credential.getId());
    credentialRepository.save(credential);

    log.info("Successfully stored the credential with ID: {}", credential.getId());
    log.trace("Exiting createCredential method in service");
    return credential;
  }

  public Credential getCredentialById(String id) {
    log.trace("Entering getCredentialById method in service");
    log.info("Attempting to fetch credential with ID: {}", id);
    return credentialRepository
        .findById(id)
        .map(
            credential -> {
              log.debug("Credential found with ID: {}", id);
              return credential;
            })
        .orElseThrow(
            () -> {
              log.error("Credential not found with ID: {} ", id);
              return new ResourceNotFoundException("Credential not found with ID " + id);
            });
  }

  public List<Credential> getAllCredentials() {
    log.info("Calling Repository to get all the credentials");
    List<Credential> credentials = credentialRepository.findAll();
    log.info("Successfuly fetched {} credentials", credentials.size());
    return credentials;
  }
}

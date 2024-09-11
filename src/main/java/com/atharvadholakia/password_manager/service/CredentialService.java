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

    log.info("Calling repository from service");
    credentialRepository.save(credential);

    log.debug("Exiting createCredential from service");
    return credential;
  }

  public Credential getCredentialById(String id) {
    log.info("Calling repository from service");
    return credentialRepository
        .findById(id)
        .orElseThrow(
            () -> {
              return new ResourceNotFoundException("Credential not found with ID " + id);
            });
  }

  public List<Credential> getAllCredentials() {
    log.info("Calling Repository to get all the credentials");
    return credentialRepository.findAll();
  }
}

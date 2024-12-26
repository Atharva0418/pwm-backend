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
    credential.setPassword(password);
    credentialRepository.save(credential);

    log.debug("Exiting createCredential from service");
    return credential;
  }

  public Credential updateCredential(
      Credential existingCredential, String serviceName, String username, String password) {

    if (!existingCredential.getUsername().equals(username)) {
      existingCredential.setUsername(username);
    }
    if (!existingCredential.getServiceName().equals(serviceName)) {
      existingCredential.setServicename(serviceName);
    }
    if (!existingCredential.getPassword().equals(password)) {
      existingCredential.setPassword(password);
    }

    log.debug("Exiting updateCredential from service");
    credentialRepository.save(existingCredential);
    return existingCredential;
  }

  public Credential getCredentialById(String id) {
    log.info("Calling repository from service to get a credential with ID: {}", id);
    Credential credential =
        credentialRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Credential not found with ID " + id));

    credential.setPassword(credential.getPassword());
    return credential;
  }

  public List<Credential> getAllCredentials() {
    log.info("Calling repository to get all the credentials");
    List<Credential> credentials = credentialRepository.findAll();
    credentials.forEach(credential -> credential.setPassword(credential.getPassword()));
    return credentials;
  }

  public void deleteCredentialById(String id) {
    log.info("Calling repository to delete a credential with ID: {}", id);
    credentialRepository.deleteById(id);
  }
}

package com.atharvadholakia.password_manager.service;

import com.atharvadholakia.password_manager.data.Credential;
import com.atharvadholakia.password_manager.data.User;
import com.atharvadholakia.password_manager.exception.ResourceNotFoundException;
import com.atharvadholakia.password_manager.repository.CredentialRepository;
import com.atharvadholakia.password_manager.repository.UserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CredentialService {

  private final CredentialRepository credentialRepository;

  private final UserRepository userRepository;

  public CredentialService(
      CredentialRepository credentialRepository, UserRepository userRepository) {
    this.credentialRepository = credentialRepository;
    this.userRepository = userRepository;
  }

  public Credential createCredential(String email, Credential credential) {
    User user =
        userRepository
            .findByEmail(email)
            .orElseThrow(
                () -> new ResourceNotFoundException("User not found with email: " + email));

    credential.setUser(user);
    log.info("Calling repository from service to write to DB.");
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
    return credential;
  }

  public List<Credential> getAllCredentialsByUserEmail(String email) {
    log.info("Calling repository to get all the credentials of userId : {}", email);
    List<Credential> credentials = credentialRepository.findByUserEmail(email);
    return credentials;
  }

  public void deleteCredentialById(String id) {
    log.info("Calling repository to delete a credential with ID: {}", id);
    credentialRepository.deleteById(id);
  }
}

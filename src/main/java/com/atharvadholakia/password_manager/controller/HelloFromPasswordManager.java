package com.atharvadholakia.password_manager.controller;

import com.atharvadholakia.password_manager.data.Credential;
import com.atharvadholakia.password_manager.service.CredentialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloFromPasswordManager {

  private final CredentialService credentialService;

  public HelloFromPasswordManager(CredentialService credentialService) {
    this.credentialService = credentialService;
  }

  @RequestMapping(
      value = "/",
      method = {RequestMethod.GET, RequestMethod.HEAD})
  public String sayHello() {
    return "Hello from Password Manager!";
  }

  @RequestMapping(value = "/health", method = RequestMethod.HEAD)
  public ResponseEntity<Void> healthCheck() {
    log.info("Health Checkup start.");
    Credential credential =
        credentialService.createCredential("TestServiceName@1", "TestUserName@1", "TestPassword@1");

    credentialService.deleteCredentialById(credential.getId());

    log.info("Health Checkup finish.");
    return new ResponseEntity<>(HttpStatus.OK);
  }
}

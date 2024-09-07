package com.atharvadholakia.password_manager.repository;

import com.atharvadholakia.password_manager.data.Credential;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class CredentialRepositoryImpl implements CredentialRepository {

  public static final Path DATA_FILE = Paths.get("src", "main", "resources", "credentials.json");
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void save(Credential credential) {
    log.trace("Entering the save method in repository");
    List<Credential> credentials = readAll();
    log.debug("Read {} credentials from the json file", credentials.size());
    credentials.removeIf(c -> c.getUsername().equals(credential.getUsername()));
    log.debug("Removed any exisitng credential with username: {}", credential.getUsername());
    credentials.add(credential);
    log.debug("Added the credential with ID: {} to the list", credential.getId());
    writeAll(credentials);
    log.info("Successfully saved the credential with ID: {} in json file", credential.getId());
  }

  @Override
  public Optional<Credential> findById(String id) {
    log.trace("Entering findById method in repository");

    Optional<Credential> credential =
        readAll().stream().filter(c -> c.getId().equals(id)).findFirst();

    if (credential.isPresent()) {
      log.debug("Credential found with ID: {}", id);
    } else {
      log.warn("Credential not found with ID: {}", id);
    }

    return credential;
  }

  @Override
  public Optional<Credential> findByUserName(String username) {

    return readAll().stream()
        .filter(credential -> credential.getUsername().equals(username))
        .findFirst();
  }

  @Override
  public List<Credential> findAll() {
    log.trace("Entering findAll method in repository");
    List<Credential> credentials = readAll();
    log.info("Found {} credentials in the json file", credentials.size());

    return credentials;
  }

  private List<Credential> readAll() {
    log.trace("Entering readAll method in the repository");

    File file = DATA_FILE.toFile();

    if (!file.exists()) {
      log.warn("Data file {} not does not exist, returning empty list", DATA_FILE.toString());
      return List.of();
    }

    try {
      List<Credential> credentials =
          objectMapper.readValue(file, new TypeReference<List<Credential>>() {});
      log.debug("Successfully read {} credentials from the json file", credentials.size());

      return credentials;

    } catch (IOException e) {

      log.error("Failed to read credentials from the file: {}", DATA_FILE.toString(), e);
      return List.of();
    }
  }

  private void writeAll(List<Credential> credentials) {
    log.trace("Entering writeAll method in repository");

    try {

      objectMapper.writeValue(DATA_FILE.toFile(), credentials);
      log.debug("Successfully wrote {} files to the json file", credentials.size());

    } catch (IOException e) {
      log.error("Failed to write credentials to the file:{}", DATA_FILE.toString(), e);
    }
  }
}

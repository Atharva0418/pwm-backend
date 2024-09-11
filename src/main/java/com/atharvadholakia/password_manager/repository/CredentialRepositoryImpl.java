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
    log.info("Entering the save method in repository");
    List<Credential> credentials = readAll();
    credentials.removeIf(c -> c.getUsername().equals(credential.getUsername()));
    credentials.add(credential);
    writeAll(credentials);
    log.debug("Successfully saved the credential with ID: {} in json file", credential.getId());
  }

  @Override
  public Optional<Credential> findById(String id) {
    log.info("Entering findById method in repository");

    return readAll().stream().filter(c -> c.getId().equals(id)).findFirst();
  }

  @Override
  public Optional<Credential> findByUserName(String username) {

    return readAll().stream()
        .filter(credential -> credential.getUsername().equals(username))
        .findFirst();
  }

  @Override
  public List<Credential> findAll() {
    log.info("Entering findAll method in repository");
    return readAll();
  }

  private List<Credential> readAll() {
    log.debug("Entering readAll method in the repository");

    File file = DATA_FILE.toFile();

    if (!file.exists()) {
      log.warn("Data file {} not does not exist, returning empty list", DATA_FILE.toString());
      return List.of();
    }

    try {
      List<Credential> credentials =
          objectMapper.readValue(file, new TypeReference<List<Credential>>() {});

      return credentials;

    } catch (IOException e) {

      log.error("Failed to read credentials from the file: {}", DATA_FILE.toString(), e);
      return List.of();
    }
  }

  private void writeAll(List<Credential> credentials) {
    log.debug("Entering writeAll method in repository");

    try {

      objectMapper.writeValue(DATA_FILE.toFile(), credentials);
      log.debug("Successfully wrote {} files to the json file", credentials.size());

    } catch (IOException e) {
      log.error("Failed to write credentials to the file:{}", DATA_FILE.toString(), e);
    }
  }
}

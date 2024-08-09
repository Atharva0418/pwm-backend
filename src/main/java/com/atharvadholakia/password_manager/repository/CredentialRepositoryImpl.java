package com.atharvadholakia.password_manager.repository;

import com.atharvadholakia.password_manager.data.Credential;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class CredentialRepositoryImpl implements CredentialRepository {

  public static final String DATA_FILE =
      "C:\\Atharva\\Java\\Password_manager\\pwm-backend\\src\\main\\java\\com\\atharvadholakia\\password_manager\\r"
          + "epository\\credentials.json";
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void save(Credential credential) {
    List<Credential> credentials = readAll();
    credentials.removeIf(c -> c.getUsername().equals(credential.getUsername()));
    credentials.add(credential);
    writeAll(credentials);
  }

  @Override
  public Optional<Credential> findById(String id) {

    return readAll().stream()
        .filter(credential -> credential.getId().equals(id))
        .findFirst();
  }

  @Override 
  public Optional<Credential> findByUsername(String username){

    return readAll().stream()
            .filter(credential -> credential.getUsername().equals(username))
            .findFirst();
  }

  @Override
  public List<Credential> findAll() {
    return readAll();
  }

  private List<Credential> readAll() {

    File file = new File(DATA_FILE);

    if (!file.exists()) {
      return List.of();
    }

    try {
      return objectMapper.readValue(file, new TypeReference<List<Credential>>() {});
    } catch (IOException e) {
      e.printStackTrace();
      return List.of();
    }
  }

  private void writeAll(List<Credential> credentials) {

    try {

      objectMapper.writeValue(new File(DATA_FILE), credentials);

    } catch (IOException e) {

      e.printStackTrace();
    }
  }
}

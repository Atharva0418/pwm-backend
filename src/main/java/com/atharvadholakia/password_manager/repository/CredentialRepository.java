package com.atharvadholakia.password_manager.repository;

import com.atharvadholakia.password_manager.data.Credential;
import java.util.List;
import java.util.Optional;

public interface CredentialRepository {
  void save(Credential credential);

  Optional<Credential> findById(String id);

  Optional<Credential> findByUserName(String username);

  public List<Credential> findAll();
}

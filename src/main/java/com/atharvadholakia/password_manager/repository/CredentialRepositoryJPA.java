package com.atharvadholakia.password_manager.repository;

import com.atharvadholakia.password_manager.data.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepositoryJPA extends JpaRepository<Credential, String> {}

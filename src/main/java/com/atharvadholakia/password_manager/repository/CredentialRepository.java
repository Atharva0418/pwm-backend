package com.atharvadholakia.password_manager.repository;

import com.atharvadholakia.password_manager.data.Credential;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, String> {

  List<Credential> findByUserEmail(String email);

  @Transactional
  @Modifying
  @Query("DELETE FROM Credential c WHERE c.user.email = :email")
  void deleteAllCredentialsByEmail(@Param("email") String email);
}

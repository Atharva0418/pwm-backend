package com.atharvadholakia.password_manager.repository;

import com.atharvadholakia.password_manager.data.User;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findByEmail(String email);

  @Transactional
  @Modifying
  @Query("UPDATE User u SET u.isDeleted = true WHERE u.id = :id")
  void softDeleteUserById(@Param("id") String id);
}

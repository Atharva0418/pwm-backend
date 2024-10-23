package com.atharvadholakia.password_manager.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;

@Entity
public class User {

  @Id private String ID;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(name = "hashed_password", nullable = false, unique = true)
  private String hashedPassword;

  @Column(nullable = true, unique = true)
  private String salt;

  public User() {}

  public User(String username, String hashedPassword, String salt) {
    this.ID = UUID.randomUUID().toString();
    this.username = username;
    this.hashedPassword = hashedPassword;
    this.salt = salt;
  }

  public String getID() {
    return ID;
  }

  public String getUsername() {
    return username;
  }

  public String getHashedPassword() {
    return hashedPassword;
  }

  public String getSalt() {
    return salt;
  }
}

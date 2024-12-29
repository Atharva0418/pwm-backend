package com.atharvadholakia.password_manager.data;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Entity
public class User {

  @Id private String userId;

  @NotBlank(message = "Email cannot be empty!")
  @Column(unique = true, nullable = false)
  private String email;

  @NotBlank(message = "Hashed password cannot be empty!")
  private String hashedPassword;

  @NotBlank(message = "Salt cannot be empty!")
  private String salt;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Credential> credentials;

  @Override
  public String toString() {
    return "User: "
        + "email='"
        + email
        + '\''
        + ", hashed password='"
        + hashedPassword
        + '\''
        + ", salt='"
        + salt
        + '\'';
  }

  public User() {}

  public User(String email, String hashedPassword, String salt) {
    this.userId = UUID.randomUUID().toString();
    this.email = email;
    this.hashedPassword = hashedPassword;
    this.salt = salt;
  }

  public String getUserId() {
    return userId;
  }

  public String getEmail() {
    return email;
  }

  public String getHashedPassword() {
    return hashedPassword;
  }

  public String getSalt() {
    return salt;
  }
}

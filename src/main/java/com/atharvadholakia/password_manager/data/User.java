package com.atharvadholakia.password_manager.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
public class User {

  @Id private String ID;

  @NotBlank(message = "Email cannot be empty!")
  private String email;

  @NotBlank(message = "Hashed password cannot be empty!")
  private String hashedPassword;

  @NotBlank(message = "Salt cannot be empty!")
  private String salt;

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
    this.ID = UUID.randomUUID().toString();
    this.email = email;
    this.hashedPassword = hashedPassword;
    this.salt = salt;
  }

  public String getID() {
    return ID;
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

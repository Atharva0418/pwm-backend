package com.atharvadholakia.password_manager.data;

import com.atharvadholakia.password_manager.validation.annotation.ValidStringType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

@Entity
public class User {

  @Id private String ID;

  @Email(message = "Email should be valid.")
  @NotBlank(message = "Email cannot be empty!")
  @Column(nullable = false, unique = true)
  private String email;

  @ValidStringType(fieldName = "Hashed password", message = "Hashed password can only be a string.")
  @NotBlank(message = "Hashed password cannot be empty!")
  @Size(min = 60, max = 255, message = "Hashed password must be between 60 and 255 characters.")
  @Column(name = "hashed_password", nullable = false, unique = true)
  private String hashedPassword;

  @ValidStringType(fieldName = "Salt", message = "Salt can only be a string.")
  @NotBlank(message = "Salt cannot be empty!")
  @Size(min = 16, max = 64, message = "Salt must be between 16 and 64 characters.")
  @Column(nullable = false, unique = true)
  private String salt;

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

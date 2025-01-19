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

  @Id private String id;

  @NotBlank(message = "Email cannot be empty!")
  @Column(unique = true, nullable = false)
  private String email;

  @NotBlank(message = "Hashed password cannot be empty!")
  private String hashedPassword;

  @NotBlank(message = "Salt cannot be empty!")
  private String salt;

  private boolean isDeleted = false;

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
    this.id = UUID.randomUUID().toString();
    this.email = email;
    this.hashedPassword = hashedPassword;
    this.salt = salt;
  }

  public String getId() {
    return id;
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

  public boolean getIsDeleted() {
    return isDeleted;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setIsDeleted(boolean isDeleted) {
    this.isDeleted = isDeleted;
  }
}

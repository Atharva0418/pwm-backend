package com.atharvadholakia.password_manager.data;

import com.atharvadholakia.password_manager.validation.annotation.ValidStringType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.UUID;

@Entity
public class Credential {

  @Id private String id;

  @NotBlank(message = "Servicename cannot be empty!")
  @Size(min = 3, max = 25, message = "Servicename must be between 3 to 25 characters.")
  @Pattern(
      regexp = "^[a-zA-Z0-9._\\- ]+$",
      message =
          "Invalid charactes. Only alphanumerics, dots, underscores, hyphens and spaces are"
              + " allowed.")
  @ValidStringType(fieldName = "Servicename", message = "Servicename can only be a string.")
  private String serviceName;

  @NotBlank(message = "Username cannot be empty!")
  @Size(min = 3, max = 25, message = "Username must be between 3 to 25 characters.")
  @Pattern(
      regexp = "^[a-zA-Z0-9._]+$",
      message =
          "Invalid characters. Only alphanumerics, dots, underscores and hyphens are allowed.")
  @ValidStringType(fieldName = "Username", message = "Username can only be a string.")
  private String username;

  @NotBlank(message = "Password cannot be empty!")
  @Size(min = 8, max = 25, message = "Password must be between 8 to 25 characters.")
  @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]+$",
      message =
          "Password must include 1 uppercase, 1 lowercase, 1 digit, 1 special character"
              + " and no spaces.")
  @ValidStringType(fieldName = "Password", message = "Password can only be a string.")
  private String password;

  @Override
  public String toString() {
    return "Credential: "
        + "serviceName='"
        + serviceName
        + '\''
        + ", username='"
        + username
        + '\''
        + ", password='"
        + password
        + '\'';
  }

  public Credential() {}

  public Credential(String serviceName, String username, String password) {
    this.id = UUID.randomUUID().toString();
    this.serviceName = serviceName.trim();
    this.username = username;
    this.password = password;
  }

  public String getId() {
    return id;
  }

  public String getServiceName() {
    return serviceName;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setServicename(String serviceName) {
    this.serviceName = serviceName;
  }
}

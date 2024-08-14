package com.atharvadholakia.password_manager.data;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public class Credential {

  private String id;

  @NotBlank(message = "Servicename cannot be empty!")
  private String serviceName;

  @NotBlank(message = "Username cannot be empty!")
  private String username;

  @NotBlank(message = "Password cannot be empty!")
  private String password;

  public Credential() {}

  public Credential(String serviceName, String username, String password) {
    this.id = UUID.randomUUID().toString();
    this.serviceName = serviceName;
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

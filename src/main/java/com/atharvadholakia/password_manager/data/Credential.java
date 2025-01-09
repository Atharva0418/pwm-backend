package com.atharvadholakia.password_manager.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
public class Credential {

  @Id private String id;

  @NotBlank(message = "Servicename cannot be empty!")
  private String serviceName;

  @NotBlank(message = "Username cannot be empty!")
  private String username;

  @NotBlank(message = "Password cannot be empty!")
  private String password;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "email", referencedColumnName = "email", nullable = false)
  @JsonIgnore
  private User user;

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

  public Credential() {
    this.id = UUID.randomUUID().toString();
  }

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

  public User getUser() {
    return user;
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

  public void setUser(User user) {
    this.user = user;
  }
}

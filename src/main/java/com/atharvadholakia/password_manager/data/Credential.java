package com.atharvadholakia.password_manager.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Credential {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String credentialId;

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

  public Credential() {}

  public Credential(String serviceName, String username, String password) {
    this.serviceName = serviceName.trim();
    this.username = username;
    this.password = password;
  }

  public String getCredentialId() {
    return credentialId;
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

  public void setCredentialId(String credentialId) {
    this.credentialId = credentialId;
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

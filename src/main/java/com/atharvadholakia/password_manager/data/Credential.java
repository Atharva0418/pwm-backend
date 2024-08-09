package com.atharvadholakia.password_manager.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class Credential {

  @JsonProperty("id")
  private String id;

  @JsonProperty("servicename")
  private String servicename;

  @JsonProperty("username")
  private String username;

  @JsonProperty("password")
  private String password;

  public Credential() {}

  public Credential(String servicename, String username, String password) {
    this.id = UUID.randomUUID().toString().substring(0, 8);
    this.servicename = servicename;
    this.username = username;
    this.password = password;
  }

  public String getId() {
    return id;
  }

  public String getServicename() {
    return servicename;
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

  public void setServicename(String servicename) {
    this.servicename = servicename;
  }
}

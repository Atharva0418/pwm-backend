package com.atharvadholakia.password_manager.data;

import com.atharvadholakia.password_manager.validation.annotation.ValidStringType;
import com.atharvadholakia.password_manager.validation.group.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public class Credential {

  private String id;

  @NotBlank(message = "serviceName cannot be empty!", groups = FirstGroup.class)
  @Size(
      min = 3,
      max = 25,
      message = "serviceName must be between 3 to 25 characters.",
      groups = SecondGroup.class)
  @Pattern(
      regexp = "^[a-zA-Z0-9._\\- ]+$",
      message =
          "Invalid charactes. Only alphanumerics, dots, underscores, hyphens and spaces are"
              + " allowed.",
      groups = ThirdGroup.class)
  @ValidStringType(
      fieldName = "serviceName",
      message = "serviceName can only be a string.",
      groups = FirstGroup.class)
  private String serviceName;

  @NotBlank(message = "username cannot be empty!", groups = FirstGroup.class)
  @Size(
      min = 3,
      max = 25,
      message = "username must be between 3 to 25 characters.",
      groups = SecondGroup.class)
  @Pattern(
      regexp = "^[a-zA-Z0-9._]+$",
      message =
          "Invalid characters. Only alphanumerics, dots, underscores and hyphens are allowed.",
      groups = ThirdGroup.class)
  @ValidStringType(
      fieldName = "username",
      message = "username can only be a string.",
      groups = FirstGroup.class)
  private String username;

  @NotBlank(message = "password cannot be empty!", groups = FirstGroup.class)
  @Size(
      min = 8,
      max = 25,
      message = "password must be between 8 to 25 characters.",
      groups = SecondGroup.class)
  @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]+$",
      message =
          "password must include 1 uppercase, 1 lowercase, 1 digit, 1 special character"
              + " and no spaces.",
      groups = ThirdGroup.class)
  @ValidStringType(
      fieldName = "password",
      message = "password can only be a string.",
      groups = FirstGroup.class)
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

package com.tb.ct.model;

import lombok.Data;

import java.util.Objects;

@Data
public class LoginRequest {

  private String username;
  private String password;

  public LoginRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    LoginRequest that = (LoginRequest) o;
    return Objects.equals(username, that.username) && Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password);
  }
}
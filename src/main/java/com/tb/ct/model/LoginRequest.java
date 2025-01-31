package com.tb.ct.model;

import lombok.Data;

import java.util.Objects;

@Data
public class LoginRequest {

  private String username;
  private String password;
}
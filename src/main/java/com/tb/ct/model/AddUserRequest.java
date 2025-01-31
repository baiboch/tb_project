package com.tb.ct.model;

import lombok.Data;

@Data
public class AddUserRequest {
  private String username;
  private String password;
}

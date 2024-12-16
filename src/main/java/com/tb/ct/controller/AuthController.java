package com.tb.ct.controller;

import com.tb.ct.model.JwtAuthenticationResponse;
import com.tb.ct.model.LoginRequest;
import com.tb.ct.model.AddUserRequest;
import com.tb.ct.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationService authenticationService;

  @Autowired
  public AuthController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping(value = "/adduser", produces = "application/json")
  public ResponseEntity<JwtAuthenticationResponse> addUser(@RequestBody @Valid AddUserRequest request) {
    return ResponseEntity.ok(authenticationService.addNewUser(request));
  }

  @PostMapping(value = "/login", produces = "application/json")
  public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody @Valid LoginRequest request) {
    return ResponseEntity.ok(authenticationService.login(request));
  }

}

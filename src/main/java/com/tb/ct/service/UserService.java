package com.tb.ct.service;

import com.tb.ct.persistency.User;
import com.tb.ct.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getCurrentUser() {
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    return getByUsername(username);
  }

  public User save(User user) {
    return userRepository.save(user);
  }

  public User create(User user) {
    if (userRepository.existsByUsername(user.getUsername())) {
      throw new RuntimeException("user already exists");
    }
    return save(user);
  }

  public User getByUsername(String username) {
    return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

  }

  public UserDetailsService userDetailsService() {
    return this::getByUsername;
  }
}

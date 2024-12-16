package com.tb.ct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CtApplication {

  public static void main(String[] args) {
    SpringApplication.run(CtApplication.class, args);
  }
}

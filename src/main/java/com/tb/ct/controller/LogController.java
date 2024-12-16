package com.tb.ct.controller;

import com.tb.ct.service.LogProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
public class LogController {

  private final LogProcessingService logProcessingService;

  public LogController(LogProcessingService logProcessingService) {
    this.logProcessingService = logProcessingService;
  }

  @PostMapping(produces = "text/plain")
  public ResponseEntity<String> processLogFile(@RequestParam String fileName) {
    logProcessingService.processLogFile(fileName);
    return ResponseEntity.ok("Processing started for file: " + fileName);
  }
}

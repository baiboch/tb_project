package com.tb.ct.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.tb.ct.exception.XmlProcessException;
import com.tb.ct.service.XmlProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xml")
public class XmlController {

  private final XmlProcessingService xmlProcessingService;

  public XmlController(XmlProcessingService xmlProcessingService) {
    this.xmlProcessingService = xmlProcessingService;
  }

  @PostMapping(consumes = "application/xml", produces = "text/plain")
  public ResponseEntity<String> processXml(@RequestBody String xmlData) {
    try {
      String logFileName = xmlProcessingService.processXmlRequest(xmlData);
      return ResponseEntity.ok("Processed: " + logFileName);
    } catch (XmlProcessException e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
    }
  }
}

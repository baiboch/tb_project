package com.tb.ct.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tb.ct.exception.InvalidXmlFormatException;
import com.tb.ct.exception.XmlProcessException;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
public class XmlProcessingService {

  private final LogFileService logFileService;

  public XmlProcessingService(LogFileService logFileService) {
    this.logFileService = logFileService;
  }

  public void process(String xmlRequest) throws XmlProcessException {
    XmlMapper xmlMapper = new XmlMapper();
    try {
      String xmlStr = clearXmlRequestData(xmlRequest);
      JsonNode jsonNode = xmlMapper.readTree(xmlStr);

      String xmlType = jsonNode.at("/Type").asText();
      System.out.println(xmlType);

      if (xmlType.isEmpty()) {
        throw new IllegalArgumentException("missing type field in Xml request");
      }
      logFileService.createLogDirIfNotExist();
      File logFile = logFileService.createLogFile(xmlType);

      var json = jsonNode.toString();
      logFileService.writeToFile(logFile, json);

    } catch (JsonProcessingException e) {
      throw new InvalidXmlFormatException(e.getMessage(), e);
    } catch (Exception e) {
      throw new XmlProcessException("Error while processing xml request: " + e.getMessage(), e);
    }
  }

  public String clearXmlRequestData(String requestXmlStr) {
    if (requestXmlStr == null || requestXmlStr.isBlank()) {
      throw new IllegalArgumentException("xml request string is null or blank");
    }
    return requestXmlStr.replaceAll(">\\s+<", "><")
            .replaceAll("\\s+", " ")
            .trim();
  }
}

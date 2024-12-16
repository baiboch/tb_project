package com.tb.ct.service;

import static com.tb.ct.util.Constant.LOG_DIR;
import static com.tb.ct.util.Constant.LOG_FILENAME;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tb.ct.exception.InvalidXmlFormatException;
import com.tb.ct.exception.XmlProcessException;
import com.tb.ct.model.JsonTypeResult;
import org.springframework.stereotype.Service;
import java.io.File;
import java.time.LocalDate;

@Service
public class XmlProcessingService {

  private final FileService fileService;

  public XmlProcessingService(FileService fileService) {
    this.fileService = fileService;
  }

  public String processXmlRequest(String xmlRequest) throws XmlProcessException {
    try {
      String xmlStr = clearXmlRequestData(xmlRequest);
      var jsonResult = getJsonTypeResult(xmlStr);
      String jsonText = jsonResult.getJsonNode().toString();

      String fileName = String.format(LOG_FILENAME, LOG_DIR, jsonResult.getType(), LocalDate.now());
      File logFile = fileService.getLogFile(null, fileName);
      fileService.writeToLogFile(logFile, jsonText);

      return logFile.getName();
    } catch (JsonProcessingException e) {
      throw new InvalidXmlFormatException(e.getMessage(), e);
    } catch (Exception e) {
      throw new XmlProcessException("Error while processing xml request: " + e.getMessage(), e);
    }
  }

  private JsonTypeResult getJsonTypeResult(String xml) throws JsonProcessingException, IllegalArgumentException {
    XmlMapper xmlMapper = new XmlMapper();
    JsonNode jsonNode = xmlMapper.readTree(xml);

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode rootNode = objectMapper.createObjectNode();
    rootNode.set("Data", jsonNode);

    String type = jsonNode.at("/Type").asText();
    if (type.isEmpty()) {
      throw new IllegalArgumentException("missing type field in Xml request");
    }
    return new JsonTypeResult(rootNode, type);
  }

  private String clearXmlRequestData(String requestXmlStr) {
    if (requestXmlStr == null || requestXmlStr.isBlank()) {
      throw new IllegalArgumentException("xml request string is null or blank");
    }
    return requestXmlStr.replaceAll(">\\s+<", "><")
            .replaceAll("\\s+", " ")
            .trim();
  }
}

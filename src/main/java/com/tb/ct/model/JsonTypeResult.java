package com.tb.ct.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.Objects;

@Data
public class JsonTypeResult {

  private JsonNode jsonNode;
  private String type;
}

package com.tb.ct.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class JsonTypeResult {

  private JsonNode jsonNode;
  private String type;
}

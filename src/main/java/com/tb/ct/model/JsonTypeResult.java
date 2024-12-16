package com.tb.ct.model;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Objects;

public class JsonTypeResult {

  private JsonNode jsonNode;
  private String type;

  public JsonTypeResult(JsonNode jsonNode, String type) {
    this.jsonNode = jsonNode;
    this.type = type;
  }

  public JsonNode getJsonNode() {
    return jsonNode;
  }

  public String getType() {
    return type;
  }

  public void setJsonNode(JsonNode jsonNode) {
    this.jsonNode = jsonNode;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    JsonTypeResult jsonTypeResult = (JsonTypeResult) o;
    return Objects.equals(type, jsonTypeResult.type) && Objects.equals(jsonNode, jsonTypeResult.jsonNode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(jsonNode, type);
  }
}

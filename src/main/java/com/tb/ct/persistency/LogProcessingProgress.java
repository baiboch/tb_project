package com.tb.ct.persistency;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "log_processing_progress")
public class LogProcessingProgress {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String fileName;

  private int batchNumber;

  private boolean processed;

  public LogProcessingProgress() {
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public void setBatchNumber(int batchNumber) {
    this.batchNumber = batchNumber;
  }

  public void setProcessed(boolean processed) {
    this.processed = processed;
  }

  public LogProcessingProgress(String fileName, int batchNumber, boolean processed) {
    this.fileName = fileName;
    this.batchNumber = batchNumber;
    this.processed = processed;
  }

  public Long getId() {
    return id;
  }

  public String getFileName() {
    return fileName;
  }

  public int getBatchNumber() {
    return batchNumber;
  }

  public boolean isProcessed() {
    return processed;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    LogProcessingProgress that = (LogProcessingProgress) o;
    return batchNumber == that.batchNumber && processed == that.processed && Objects.equals(id, that.id) && Objects.equals(fileName, that.fileName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, fileName, batchNumber, processed);
  }
}

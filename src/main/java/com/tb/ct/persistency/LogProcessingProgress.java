package com.tb.ct.persistency;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
@Table(name = "log_processing_progress")
public class LogProcessingProgress {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String fileName;

  private int batchNumber;

  private boolean processed;
}

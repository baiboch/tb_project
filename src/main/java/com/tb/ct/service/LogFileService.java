package com.tb.ct.service;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

@Service
public class LogFileService {

  private static final String LOG_DIR = "logs/";
  private static final String LOG_FILENAME = "%s_%s_%s.log";
  private static final String TOTAL_RECORDS_MASK = "TotalRecords: %s";

  public void createLogDirIfNotExist() {
    File logDir = new File(LOG_DIR);
    if (!logDir.exists()) {
      try {
        if (!logDir.mkdirs()) {
          throw new IOException("Failed to create dir: " + LOG_DIR);
        }
      } catch (IOException e) {
        throw new IllegalStateException("Could not create log directory", e);
      }
    }
  }

  public File createLogFile(String type) {
    String fileName = String.format(LOG_FILENAME, LOG_DIR, type, LocalDate.now());
    return new File(fileName);
  }

  public void writeToFile(File file, String jsonString) throws IOException {
    boolean isNew = !file.exists();
    try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file, true))) {
      if (isNew) {
        String totalTitle = getTotalRecordsTitle(0);
        writer.write(totalTitle);
        writer.newLine();
      }
      writer.write(jsonString);
      writer.newLine();
    } catch (IOException e) {
      throw new IllegalStateException(
              "Could not write to file: " + file.getAbsolutePath(), e);
    }
    updateRecordCount(file);
  }

  public void updateRecordCount(File file) throws IllegalStateException {
    try {
      List<String> lines = Files.readAllLines(file.toPath());
      int recordsCount = lines.size() - 1;
      String title = getTotalRecordsTitle(recordsCount);
      lines.set(0, title);
      Files.write(file.toPath(), lines);
    } catch (IOException e) {
      throw new IllegalStateException(
              "Could not update records count: " + file.getAbsolutePath());
    }
  }

  public String getTotalRecordsTitle(int count) {
    return String.format(TOTAL_RECORDS_MASK, count);
  }
}

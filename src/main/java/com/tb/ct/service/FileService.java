package com.tb.ct.service;

import static com.tb.ct.util.Constant.LOG_DIR;
import static com.tb.ct.util.Constant.TOTAL_RECORDS;

import org.springframework.stereotype.Service;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class FileService {

  public void saveBatchToFile(List<String> batch, String fileName) throws IOException {
    Path outputPath = Path.of(fileName);
    try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(outputPath.toFile(), true))) {
      for (String line : batch) {
        writer.write(line + "\n");
      }
    }
  }

  public void createLogDirIfNotExist(String dir) throws IllegalStateException {
    File logDir = new File(dir == null ? LOG_DIR : LOG_DIR + dir + "/");
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

  public File getLogFile(String dir, String fileName) {
    createLogDirIfNotExist(dir);
    return new File(fileName);
  }

  public void writeToLogFile(File file, String jsonString) throws IOException {
    boolean isNew = !file.exists();
    try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file, true))) {
      if (isNew) {
        String totalTitle = getTotalRecordsTitle(0);
        writer.write(totalTitle + "\n");
      }
      writer.write(jsonString + "\n");
    } catch (IOException e) {
      throw new IllegalStateException(
              "Could not write to file: " + file.getAbsolutePath(), e);
    }
    updateRecordCount(file);
  }

  private void updateRecordCount(File file) throws IllegalStateException {
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

  private String getTotalRecordsTitle(int count) {
    return String.format(TOTAL_RECORDS, count);
  }
}

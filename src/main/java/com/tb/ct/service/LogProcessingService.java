package com.tb.ct.service;

import static com.tb.ct.util.Constant.LOG_DIR;
import static com.tb.ct.util.Constant.BATCH_LOG_DIR;

import com.tb.ct.exception.LogFileProcessException;
import com.tb.ct.persistency.LogProcessingProgress;
import com.tb.ct.repository.LogProcessingRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogProcessingService {

  private static final int BATCH_SIZE = 1;

  private final FileService fileService;
  private final LogProcessingRepository progressRepository;

  public LogProcessingService(FileService fileService, LogProcessingRepository progressRepository) {
    this.fileService = fileService;
    this.progressRepository = progressRepository;
  }

  @Async
  @Transactional
  public void processLogFile(String fileName) throws LogFileProcessException {
    try {
      File inputFile = new File(LOG_DIR + fileName);
      if (!inputFile.exists()) {
        throw new IllegalStateException("Log file does not exist: " + fileName);
      }
      int lastBatchNumber = getLastProcessedBatchNumber(fileName);
      try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
        skipProcessedLines(reader, lastBatchNumber);

        String totalRecords = reader.readLine();
        List<String> batch = new ArrayList<>();
        int currentBatchNumber = lastBatchNumber + 1;
        String line;

        while ((line = reader.readLine()) != null) {
          line = line.trim();
          if (!line.isEmpty()) {
            batch.add(line);
          }
          if (batch.size() == BATCH_SIZE) {
            saveBatch(batch, fileName, currentBatchNumber);
            currentBatchNumber++;
            batch.clear();
          }
        }
        if (batch.size() > 0) {
          saveBatch(batch, fileName, currentBatchNumber);
        }
        System.out.println("Processing completed for file: " + fileName);
      } catch (IOException e) {
        throw new IllegalStateException("Error processing file", e);
      }

    } catch (IllegalStateException e) {
      throw new LogFileProcessException("Could not read file: " + fileName, e);
    }
  }

  private int getLastProcessedBatchNumber(String fileName) {
    LogProcessingProgress progress = progressRepository.findTopByFileNameOrderByBatchNumberDesc(fileName);
    return progress != null ? progress.getBatchNumber() : 0;
  }

  private void skipProcessedLines(BufferedReader reader, int processedBatches) throws IOException {
    int linesToSkip = processedBatches * BATCH_SIZE;
    for (int i = 0; i < linesToSkip; i++) {
      reader.readLine();
    }
  }

  private void saveBatch(List<String> batch, String fileName, int batchNumber) throws IOException {
    fileService.createLogDirIfNotExist(BATCH_LOG_DIR);
    String outputLogFileName = String.format("output_logs/batch_logs/%s-%04d.log", fileName, batchNumber);

    fileService.saveBatchToFile(batch, outputLogFileName);
    saveLogProgress(fileName, batchNumber);
  }

  private void saveLogProgress(String fileName, int batchNumber) {
    LogProcessingProgress progress = new LogProcessingProgress();
    progress.setFileName(fileName);
    progress.setBatchNumber(batchNumber);
    progress.setProcessed(true);
    progressRepository.save(progress);
  }
}

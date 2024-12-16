package com.tb.ct.repository;

import com.tb.ct.persistency.LogProcessingProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogProcessingRepository extends JpaRepository<LogProcessingProgress, Long> {
  LogProcessingProgress findTopByFileNameOrderByBatchNumberDesc(String fileName);
}

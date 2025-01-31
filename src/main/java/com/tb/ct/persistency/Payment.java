package com.tb.ct.persistency;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@Entity
@Table(name = "payments")
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String paymentId;

  @Column(nullable = false, precision = 19, scale = 4)
  private BigDecimal amount;

  @Column(nullable = false)
  private String status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
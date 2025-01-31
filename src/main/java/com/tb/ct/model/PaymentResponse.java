package com.tb.ct.model;

import lombok.Data;

@Data
public class PaymentResponse {
  private String paymentId;
  private String status;
  private String message;
}

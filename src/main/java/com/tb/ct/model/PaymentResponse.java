package com.tb.ct.model;

import java.util.Objects;

public class PaymentResponse {
  private String paymentId;
  private String status;
  private String message;

  public PaymentResponse(String paymentId, String status, String message) {
    this.paymentId = paymentId;
    this.status = status;
    this.message = message;
  }

  public String getPaymentId() {
    return paymentId;
  }

  public String getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public void setPaymentId(String paymentId) {
    this.paymentId = paymentId;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    PaymentResponse that = (PaymentResponse) o;
    return Objects.equals(paymentId, that.paymentId) && Objects.equals(status, that.status) && Objects.equals(message, that.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(paymentId, status, message);
  }
}

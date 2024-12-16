package com.tb.ct.persistency;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

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

  public Payment(String paymentId, BigDecimal amount, String status, User user) {
    this.paymentId = paymentId;
    this.amount = amount;
    this.status = status;
    this.user = user;
  }

  public Payment() {

  }

  public Long getId() {
    return id;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getPaymentId() {
    return paymentId;
  }

  public String getStatus() {
    return status;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public void setPaymentId(String paymentId) {
    this.paymentId = paymentId;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Payment payment = (Payment) o;
    return Objects.equals(id, payment.id) && Objects.equals(paymentId, payment.paymentId) && Objects.equals(amount, payment.amount) && Objects.equals(status, payment.status) && Objects.equals(user, payment.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, paymentId, amount, status, user);
  }
}
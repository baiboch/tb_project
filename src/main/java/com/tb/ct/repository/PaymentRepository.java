package com.tb.ct.repository;

import com.tb.ct.persistency.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
  Optional<Payment> findByPaymentId(String paymentId);

}

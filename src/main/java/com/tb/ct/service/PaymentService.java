package com.tb.ct.service;

import com.tb.ct.model.PaymentResponse;
import com.tb.ct.persistency.Payment;
import com.tb.ct.persistency.User;
import com.tb.ct.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PaymentService {
  private static final String SUCCESS = "success";
  private static final String AMOUNT_TO_DEDUCT = "1.10";
  private static final String FAILED = "failed";

  private final UserService userService;
  private final PaymentRepository paymentRepository;

  public PaymentService(UserService userService, PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
    this.userService = userService;
  }

  @Transactional
  public PaymentResponse processPayment() {
    User currentUser = userService.getCurrentUser();

    BigDecimal totalPayments = currentUser.getPayments().stream()
            .map(Payment::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal amountToDeduct = new BigDecimal(AMOUNT_TO_DEDUCT).setScale(2, BigDecimal.ROUND_HALF_UP);

    BigDecimal newBalance = currentUser.getBalance().subtract(totalPayments).subtract(amountToDeduct);
    if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
      return new PaymentResponse(null, FAILED, "Insufficient balance after deduction.");
    }
    UUID uuid = UUID.randomUUID();
    Payment payment = new Payment(
            uuid.toString(),
            amountToDeduct,
            SUCCESS,
            currentUser
    );
    currentUser.setBalance(currentUser.getBalance().subtract(amountToDeduct));
    userService.save(currentUser);
    paymentRepository.save(payment);

    return new PaymentResponse(payment.getPaymentId(), SUCCESS, "Payment processed successfully.");
  }
}
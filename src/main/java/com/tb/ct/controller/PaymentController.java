package com.tb.ct.controller;

import com.tb.ct.model.PaymentResponse;
import com.tb.ct.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping(value = "/process", produces = "application/json")
  public ResponseEntity<PaymentResponse> processPayment() {
    PaymentResponse response = paymentService.processPayment();
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}

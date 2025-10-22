package com.ecdms.ecdms.service;

import com.ecdms.ecdms.dto.request.PaymentFilterDTO;
import com.ecdms.ecdms.dto.request.PaymentReceiptDTO;
import com.ecdms.ecdms.dto.response.PaymentDTO;
import org.springframework.http.ResponseEntity;

public interface PaymentService {

    void createYearlyPaymentPlan(Integer userId, String type, double amount);

    ResponseEntity getPayments(PaymentFilterDTO paymentFilterDTO);

    ResponseEntity makePayment(PaymentDTO paymentDTO);

    ResponseEntity submitPayment(PaymentReceiptDTO paymentReceiptDTO);

    ResponseEntity rejectPayment(PaymentDTO paymentDTO);
}

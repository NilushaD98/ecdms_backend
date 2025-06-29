package com.ecdms.ecdms.service;

import com.ecdms.ecdms.dto.request.PaymentFilterDTO;
import org.springframework.http.ResponseEntity;

public interface PaymentService {

    void createYearlyPaymentPlan(Integer userId, String type, double amount);

    ResponseEntity getPayments(PaymentFilterDTO paymentFilterDTO);
}

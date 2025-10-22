package com.ecdms.ecdms.controller;

import com.ecdms.ecdms.dto.request.PaymentFilterDTO;
import com.ecdms.ecdms.dto.request.PaymentReceiptDTO;
import com.ecdms.ecdms.dto.response.PaymentDTO;
import com.ecdms.ecdms.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/get-payments")
    public ResponseEntity getPayments(@RequestBody PaymentFilterDTO paymentFilterDTO){
        return paymentService.getPayments(paymentFilterDTO);
    }

    @PostMapping("/payment-submit")
    public ResponseEntity submitPayment(@RequestBody PaymentReceiptDTO paymentReceiptDTO){
        return paymentService.submitPayment(paymentReceiptDTO);
    }
    @PostMapping("/make-payment")
    public ResponseEntity makePayment(@RequestBody PaymentDTO paymentDTO){
        return paymentService.makePayment(paymentDTO);
    }
    @PostMapping("/reject-payment")
    public ResponseEntity rejectPayment(@RequestBody PaymentDTO paymentDTO){
        return paymentService.rejectPayment(paymentDTO);
    }
}

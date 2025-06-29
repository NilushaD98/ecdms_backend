package com.ecdms.ecdms.controller;

import com.ecdms.ecdms.dto.request.PaymentFilterDTO;
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


    @GetMapping("/get-payments")
    public ResponseEntity getPayments(@RequestBody PaymentFilterDTO paymentFilterDTO){
        return paymentService.getPayments(paymentFilterDTO);
    }
}

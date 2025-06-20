package com.ecdms.ecdms.service;

public interface PaymentService {

    void createYearlyPaymentPlan(Integer userId, String type, double amount);
}

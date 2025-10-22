package com.ecdms.ecdms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentReceiptDTO {
    private Integer paymentId;
    private String receiptUrl;
    private String submittedDate;
    private Integer userId;
}
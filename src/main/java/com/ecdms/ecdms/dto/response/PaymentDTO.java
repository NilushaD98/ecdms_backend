package com.ecdms.ecdms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDTO {

    private Integer paymentId;
    private String type;
    private double amount;
    private Date dueDate;
    private Date paidDate;
    private Boolean isPendingApprove;
    private boolean paid;
    private String paymentReceipt;
}

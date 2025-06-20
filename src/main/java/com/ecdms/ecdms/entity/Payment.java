package com.ecdms.ecdms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer paymentId;
    private Integer userId;
    private String type;
    private double amount;
    private Date dueDate;
    private Date paidDate;
    private boolean paid;

    public Payment(Integer paymentId, Integer userId, String type, double amount, Date dueDate, boolean paid) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.dueDate = dueDate;
        this.paid = paid;
    }
}

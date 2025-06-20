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
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer appointmentId;
    private Integer studentID;
    private String reason;
    private Date scheduledTime;
    private boolean confirmed;

    public Appointment(Integer studentID, String reason, Date scheduledTime, boolean confirmed) {
        this.studentID = studentID;
        this.reason = reason;
        this.scheduledTime = scheduledTime;
        this.confirmed = confirmed;
    }
}

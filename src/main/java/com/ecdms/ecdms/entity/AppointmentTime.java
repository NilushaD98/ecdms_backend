package com.ecdms.ecdms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "appointmentTime")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer appointmentTime;
    private String timeRange;
}

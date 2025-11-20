package com.ecdms.ecdms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "teacher_salary")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeacherSalary {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer salaryId;
    
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    
    private Double amount;
    private String month;
    private Integer year;
    private Date paidDate;
}
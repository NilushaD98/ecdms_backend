package com.ecdms.ecdms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeacherSalaryDTO {
    private Double amount;
    private String month;
    private Integer teacherId;
    private Integer year;
}
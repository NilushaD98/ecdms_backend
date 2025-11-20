package com.ecdms.ecdms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeacherSalaryResponseDTO {
    private Integer salaryId;
    private Double amount;
    private String month;
    private Integer year;
    private Date paidDate;
}
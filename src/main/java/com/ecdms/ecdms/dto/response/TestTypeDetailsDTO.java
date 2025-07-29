package com.ecdms.ecdms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestTypeDetailsDTO {

    private Integer testTypeID;
    private String testName;
    private String description;
    private Date testDate;
    List<ExamResultDetailsDTO> examResultDetailsDTOS;
}


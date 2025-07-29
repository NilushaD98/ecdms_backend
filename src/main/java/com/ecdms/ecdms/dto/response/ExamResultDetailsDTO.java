package com.ecdms.ecdms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExamResultDetailsDTO {

    private Integer resultID;
    private String student;
    private double score;
    private double passScore;
    private boolean passStatus;
}

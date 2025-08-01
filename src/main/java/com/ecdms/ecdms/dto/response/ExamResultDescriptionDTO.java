package com.ecdms.ecdms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExamResultDescriptionDTO {

    private Integer resultID;
    private String  student;
    private String testType;
    private double score;
    private double maxScore;
    private boolean passStatus;
}

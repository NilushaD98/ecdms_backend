package com.ecdms.ecdms.dto.request;

import com.ecdms.ecdms.entity.Student;
import com.ecdms.ecdms.entity.TestType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExamResultDTO {
    private Integer resultID;
    private Integer student;
    private Integer testType;
    private double score;
    private double passScore;
    private double maxScore;
    private boolean passStatus;
}

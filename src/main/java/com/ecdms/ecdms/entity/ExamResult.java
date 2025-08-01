package com.ecdms.ecdms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "exam_result")
public class ExamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer resultID;
    @ManyToOne
    @JoinColumn(name = "studentID")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "testTypeID")
    private TestType testType;
    private double score;
    private double passScore;
    private double maxScore;
    private boolean passStatus;

    public ExamResult(Student student, TestType testType, double score, double passScore,double maxScore, boolean passStatus) {
        this.student = student;
        this.testType = testType;
        this.score = score;
        this.passScore = passScore;
        this.maxScore = maxScore;
        this.passStatus = passStatus;
    }
}

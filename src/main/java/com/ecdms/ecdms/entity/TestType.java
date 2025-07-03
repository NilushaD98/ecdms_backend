package com.ecdms.ecdms.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "test_type")
public class TestType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer testTypeID;
    private String testName;
    private String description;
    private Date testDate;
    @OneToMany(mappedBy = "testType",fetch = FetchType.LAZY)
    private List<ExamResult> examResults = new ArrayList<>();

    public TestType(String testName, String description, Date testDate) {
        this.testName = testName;
        this.description = description;
        this.testDate = testDate;
    }

    public TestType(Integer testTypeID) {
        this.testTypeID = testTypeID;
    }
}

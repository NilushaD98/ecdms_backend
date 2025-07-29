package com.ecdms.ecdms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestResultsDTO {

    private String testName;
    private double marks;
    private boolean passStatus;
    private double maxMark;

}

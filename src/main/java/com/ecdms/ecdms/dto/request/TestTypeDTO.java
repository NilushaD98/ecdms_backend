package com.ecdms.ecdms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestTypeDTO {

    private Integer testTypeID;
    private String testName;
    private String description;
    private Date testDate;
    private int testClass;
}

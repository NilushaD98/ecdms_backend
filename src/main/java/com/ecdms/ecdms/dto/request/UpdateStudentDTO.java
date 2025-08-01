package com.ecdms.ecdms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateStudentDTO {

    private Integer userID;
    private String allergies;
    private String specialNotice;
    private String contactOne;
    private String contactTwo;
}

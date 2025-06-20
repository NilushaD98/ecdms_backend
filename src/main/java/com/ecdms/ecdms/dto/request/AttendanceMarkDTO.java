package com.ecdms.ecdms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttendanceMarkDTO {

    private Date date;
    private boolean present;
    private String remark;
    private Integer student;
    private Integer teacher;
}

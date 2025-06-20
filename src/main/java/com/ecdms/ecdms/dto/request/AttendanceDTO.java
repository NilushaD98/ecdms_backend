package com.ecdms.ecdms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttendanceDTO {
    private Integer attendanceID;
    private Date date;
    private boolean present;
    private String remarks;
}

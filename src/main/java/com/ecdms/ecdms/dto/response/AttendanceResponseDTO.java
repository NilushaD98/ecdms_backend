package com.ecdms.ecdms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttendanceResponseDTO {

    private int attendanceID;
    private Date date;
    private String name;
    private String classType;
    private Boolean presentStatus;

}

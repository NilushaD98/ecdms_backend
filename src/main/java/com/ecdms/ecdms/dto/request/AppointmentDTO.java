package com.ecdms.ecdms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentDTO {
        private Integer appointmentId;
        private Integer studentID;
        private String studentName;
        private String reason;
        private Date scheduledTime;
        private boolean confirmed;
}

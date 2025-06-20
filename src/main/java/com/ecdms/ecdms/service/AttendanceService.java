package com.ecdms.ecdms.service;

import com.ecdms.ecdms.dto.request.AttendanceMarkDTO;
import org.springframework.http.ResponseEntity;

public interface AttendanceService {
    ResponseEntity studentAttendanceMark(AttendanceMarkDTO attendanceMarkDTO);

    ResponseEntity teacherAttendanceMark(AttendanceMarkDTO attendanceMarkDTO);
}

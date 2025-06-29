package com.ecdms.ecdms.service;

import com.ecdms.ecdms.dto.request.AttendanceMarkDTO;
import com.ecdms.ecdms.dto.request.AttendanceRequestDTO;
import org.springframework.http.ResponseEntity;

public interface AttendanceService {
    ResponseEntity studentAttendanceMark(AttendanceMarkDTO attendanceMarkDTO);

    ResponseEntity teacherAttendanceMark(AttendanceMarkDTO attendanceMarkDTO);

    ResponseEntity getAttendanceByDate(AttendanceRequestDTO attendanceRequestDTO);

    ResponseEntity getAttendanceByDateTeacher(AttendanceRequestDTO attendanceRequestDTO);
}

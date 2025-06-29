package com.ecdms.ecdms.controller;

import com.ecdms.ecdms.dto.request.AttendanceMarkDTO;
import com.ecdms.ecdms.dto.request.AttendanceRequestDTO;
import com.ecdms.ecdms.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
@Slf4j
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/student-attendance-mark")
    public ResponseEntity studentAttendanceMark(@RequestBody AttendanceMarkDTO attendanceMarkDTO){
        return attendanceService.studentAttendanceMark(attendanceMarkDTO);
    }
    @PostMapping("/teacher-attendance-mark")
    public ResponseEntity teacherAttendanceMark(@RequestBody AttendanceMarkDTO attendanceMarkDTO){
        return attendanceService.teacherAttendanceMark(attendanceMarkDTO);
    }
        @PostMapping("/get-attendance-by-date")
    public ResponseEntity getAttendanceByDate(@RequestBody AttendanceRequestDTO attendanceRequestDTO){
        return attendanceService.getAttendanceByDate(attendanceRequestDTO);
    }

    @PostMapping("/get-attendance-by-date-teacher")
    public ResponseEntity getAttendanceByDateTeacher(@RequestBody AttendanceRequestDTO attendanceRequestDTO){
        return attendanceService.getAttendanceByDateTeacher(attendanceRequestDTO);
    }
}

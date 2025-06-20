package com.ecdms.ecdms.service.IMPL;

import com.ecdms.ecdms.dto.common.StandardResponse;
import com.ecdms.ecdms.dto.request.AttendanceMarkDTO;
import com.ecdms.ecdms.entity.Attendance;
import com.ecdms.ecdms.entity.Student;
import com.ecdms.ecdms.entity.Teacher;
import com.ecdms.ecdms.exceptions.InternalServerErrorException;
import com.ecdms.ecdms.repository.AttendanceRepository;
import com.ecdms.ecdms.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceServiceIMPL implements AttendanceService {
    private final AttendanceRepository attendanceRepository;

    @Override
    public ResponseEntity studentAttendanceMark(AttendanceMarkDTO attendanceMarkDTO) {
        try {
            Attendance attendance = new Attendance(
                    attendanceMarkDTO.getDate(),
                    attendanceMarkDTO.isPresent(),
                    attendanceMarkDTO.getRemark(),
                    new Student(attendanceMarkDTO.getStudent())
            );
            attendanceRepository.save(attendance);
            return new ResponseEntity(new StandardResponse(true,"Attendance marked."), HttpStatus.OK);

        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred in Attendance Mark.");
        }
    }

    @Override
    public ResponseEntity teacherAttendanceMark(AttendanceMarkDTO attendanceMarkDTO) {
        try {
            Attendance attendance = new Attendance(
                    attendanceMarkDTO.getDate(),
                    attendanceMarkDTO.isPresent(),
                    attendanceMarkDTO.getRemark(),
                    new Teacher(attendanceMarkDTO.getTeacher())
            );
            attendanceRepository.save(attendance);
            return new ResponseEntity(new StandardResponse(true,"Attendance marked."), HttpStatus.OK);

        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred in Attendance Mark.");
        }
    }
}

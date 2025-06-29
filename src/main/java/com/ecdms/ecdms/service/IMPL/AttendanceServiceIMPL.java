package com.ecdms.ecdms.service.IMPL;

import com.ecdms.ecdms.dto.common.StandardResponse;
import com.ecdms.ecdms.dto.request.AttendanceMarkDTO;
import com.ecdms.ecdms.dto.request.AttendanceRequestDTO;
import com.ecdms.ecdms.dto.response.AttendanceResponseDTO;
import com.ecdms.ecdms.entity.Attendance;
import com.ecdms.ecdms.entity.Student;
import com.ecdms.ecdms.entity.Teacher;
import com.ecdms.ecdms.exceptions.InternalServerErrorException;
import com.ecdms.ecdms.repository.AttendanceRepository;
import com.ecdms.ecdms.repository.StudentRepository;
import com.ecdms.ecdms.repository.TeacherRepository;
import com.ecdms.ecdms.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceServiceIMPL implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public ResponseEntity studentAttendanceMark(AttendanceMarkDTO attendanceMarkDTO) {
        try {
            Optional<Attendance> optionalAttendance = attendanceRepository.findByStudentAndDate(attendanceMarkDTO.getStudent(),attendanceMarkDTO.getDate());
            if(optionalAttendance.isPresent()){
                optionalAttendance.get().setPresent(attendanceMarkDTO.isPresent());
                attendanceRepository.save(optionalAttendance.get());
            }else {
                Attendance attendance = new Attendance(
                        attendanceMarkDTO.getDate(),
                        attendanceMarkDTO.isPresent(),
                        attendanceMarkDTO.getRemark(),
                        new Student(attendanceMarkDTO.getStudent())
                );
                attendanceRepository.save(attendance);
            }

            return new ResponseEntity(new StandardResponse(true,"Attendance marked."), HttpStatus.OK);

        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred in Attendance Mark.");
        }
    }

    @Override
    public ResponseEntity teacherAttendanceMark(AttendanceMarkDTO attendanceMarkDTO) {
        try {
            Optional<Attendance> optionalAttendance = attendanceRepository.findByTeacherAndDate(attendanceMarkDTO.getTeacher(),attendanceMarkDTO.getDate());
            if (optionalAttendance.isPresent()){
                optionalAttendance.get().setPresent(attendanceMarkDTO.isPresent());
                attendanceRepository.save(optionalAttendance.get());
            }else {
                Attendance attendance = new Attendance(
                        attendanceMarkDTO.getDate(),
                        attendanceMarkDTO.isPresent(),
                        attendanceMarkDTO.getRemark(),
                        new Teacher(attendanceMarkDTO.getTeacher())
                );
                attendanceRepository.save(attendance);
            }
            return new ResponseEntity(new StandardResponse(true,"Attendance marked."), HttpStatus.OK);

        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred in Attendance Mark.");
        }
    }

    @Override
    public ResponseEntity getAttendanceByDate(AttendanceRequestDTO attendanceRequestDTO) {
        try {
            List<AttendanceResponseDTO> attendanceResponseDTOS = new ArrayList<>();
            List<Integer> studentIDsByDate = attendanceRepository.findStudentIDsByDate(attendanceRequestDTO.getAttendanceDate());
            List<Student> all = studentRepository.findAll();

            int i = 1;
            for(Student student:all){
                boolean contains = studentIDsByDate.contains(student.getStuID());
                AttendanceResponseDTO attendanceResponseDTO = new AttendanceResponseDTO(
                        student.getStuID(),
                        attendanceRequestDTO.getAttendanceDate(),
                        student.getFirstName()+" "+student.getLastName(),
                            student.getProgram(),
                        contains? true:false
                        );
                attendanceResponseDTOS.add(attendanceResponseDTO);
            }
            return new ResponseEntity(new StandardResponse(200,"Attendance List",attendanceResponseDTOS),HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred in get attendance by date.");
        }
    }

    @Override
    public ResponseEntity getAttendanceByDateTeacher(AttendanceRequestDTO attendanceRequestDTO) {
        try {
            List<AttendanceResponseDTO> attendanceResponseDTOS = new ArrayList<>();
            List<Integer> teacherIDsByDate = attendanceRepository.findTeacherIDsByDate(attendanceRequestDTO.getAttendanceDate());
            List<Teacher> all = teacherRepository.findAll();

            int i = 1;
            for(Teacher teacher:all){
                boolean contains = teacherIDsByDate.contains(teacher.getTeacherID());
                AttendanceResponseDTO attendanceResponseDTO = new AttendanceResponseDTO(
                        teacher.getTeacherID(),
                        attendanceRequestDTO.getAttendanceDate(),
                        teacher.getFullName(),
                        null,
                        contains? true:false
                );
                attendanceResponseDTOS.add(attendanceResponseDTO);
            }
            return new ResponseEntity(new StandardResponse(200,"Attendance List",attendanceResponseDTOS),HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred in get attendance by date.");
        }
    }
}

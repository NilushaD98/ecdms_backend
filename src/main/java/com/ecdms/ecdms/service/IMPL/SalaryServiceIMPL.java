package com.ecdms.ecdms.service.IMPL;

import com.ecdms.ecdms.dto.common.StandardResponse;
import com.ecdms.ecdms.dto.request.TeacherSalaryDTO;
import com.ecdms.ecdms.dto.response.TeacherSalaryResponseDTO;
import com.ecdms.ecdms.entity.Teacher;
import com.ecdms.ecdms.entity.TeacherSalary;
import com.ecdms.ecdms.repository.TeacherRepository;
import com.ecdms.ecdms.repository.TeacherSalaryRepository;
import com.ecdms.ecdms.service.SalaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalaryServiceIMPL implements SalaryService {
    
    private final TeacherSalaryRepository teacherSalaryRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public ResponseEntity addTeacherSalary(TeacherSalaryDTO teacherSalaryDTO) {
        try {
            Optional<Teacher> teacher = teacherRepository.findById(teacherSalaryDTO.getTeacherId());
            if (teacher.isPresent()) {
                TeacherSalary salary = new TeacherSalary();
                salary.setTeacher(teacher.get());
                salary.setAmount(teacherSalaryDTO.getAmount());
                salary.setMonth(teacherSalaryDTO.getMonth());
                salary.setYear(teacherSalaryDTO.getYear());
                salary.setPaidDate(new Date());
                
                teacherSalaryRepository.save(salary);
                return new ResponseEntity(new StandardResponse(true, "Salary added successfully"), HttpStatus.OK);
            } else {
                return new ResponseEntity(new StandardResponse(false, "Teacher not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(new StandardResponse(false, "Error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity getTeacherSalaries(Integer teacherId) {
        try {
            List<TeacherSalary> salaries = teacherSalaryRepository.findByTeacherTeacherIDOrderByYearDescMonthDesc(teacherId);
            List<TeacherSalaryResponseDTO> responseDTOs = salaries.stream()
                    .map(salary -> new TeacherSalaryResponseDTO(
                            salary.getSalaryId(),
                            salary.getAmount(),
                            salary.getMonth(),
                            salary.getYear(),
                            salary.getPaidDate()
                    ))
                    .collect(Collectors.toList());
            
            return new ResponseEntity(new StandardResponse(200, "Teacher salaries", responseDTOs), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(new StandardResponse(false, "Error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
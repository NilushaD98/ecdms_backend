package com.ecdms.ecdms.service;

import com.ecdms.ecdms.dto.request.TeacherSalaryDTO;
import org.springframework.http.ResponseEntity;

public interface SalaryService {
    ResponseEntity addTeacherSalary(TeacherSalaryDTO teacherSalaryDTO);
    ResponseEntity getTeacherSalaries(Integer teacherId);
}
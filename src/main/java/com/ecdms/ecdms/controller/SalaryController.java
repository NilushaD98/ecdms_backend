package com.ecdms.ecdms.controller;

import com.ecdms.ecdms.dto.request.TeacherSalaryDTO;
import com.ecdms.ecdms.service.SalaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salary")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class SalaryController {

    private final SalaryService salaryService;

    @PostMapping("/add-teacher-salary")
    public ResponseEntity addTeacherSalary(@RequestBody TeacherSalaryDTO teacherSalaryDTO) {
        return salaryService.addTeacherSalary(teacherSalaryDTO);
    }

    @GetMapping("/get-teacher-salaries/{teacherId}")
    public ResponseEntity getTeacherSalaries(@PathVariable Integer teacherId) {
        return salaryService.getTeacherSalaries(teacherId);
    }
}
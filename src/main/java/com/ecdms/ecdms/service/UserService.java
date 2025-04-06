package com.ecdms.ecdms.service;

import com.ecdms.ecdms.dto.request.AddStudentDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity addStudent(AddStudentDTO addStudentDTO);

    ResponseEntity getAllStudents();
}

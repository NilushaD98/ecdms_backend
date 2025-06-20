package com.ecdms.ecdms.service;

import com.ecdms.ecdms.dto.request.StudentDTO;
import org.springframework.http.ResponseEntity;

public interface ParentService {
    ResponseEntity getStudentProfile(int stuID);

    ResponseEntity updateStudentProfile(StudentDTO studentDTO);
}

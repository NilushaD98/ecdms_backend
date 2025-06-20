package com.ecdms.ecdms.controller;

import com.ecdms.ecdms.dto.request.StudentDTO;
import com.ecdms.ecdms.service.ParentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parent")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class ParentController {

    private final ParentService parentService;

    @GetMapping("/get-student-profile")
    public ResponseEntity getStudentProfile(@RequestParam ("stuID") int stuID){
        return parentService.getStudentProfile(stuID);
    }

    @PostMapping("/update-student-profile")
    public ResponseEntity updateStudentProfile(@RequestBody StudentDTO studentDTO){
        return parentService.updateStudentProfile(studentDTO);
    }
}

package com.ecdms.ecdms.controller;

import com.ecdms.ecdms.dto.request.AddStudentDTO;
import com.ecdms.ecdms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class UserController {


    private final UserService userService;

    @PostMapping("/add-student")
    public ResponseEntity addStudent(@RequestBody AddStudentDTO addStudentDTO){
        return userService.addStudent(addStudentDTO);
    }
    @GetMapping("/get-all-students")
    public ResponseEntity getAllStudent(){
        return userService.getAllStudents();
    }
    @GetMapping("/get-student-by-id")
    public ResponseEntity getStudentByID(){
        return null;
    }
}

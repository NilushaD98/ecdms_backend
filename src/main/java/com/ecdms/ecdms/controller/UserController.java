package com.ecdms.ecdms.controller;

import com.ecdms.ecdms.dto.request.AddStudentDTO;
import com.ecdms.ecdms.dto.request.TeacherDTO;
import com.ecdms.ecdms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {


    private final UserService userService;

    @PostMapping("/add-student")
    public ResponseEntity addStudent(@RequestBody AddStudentDTO addStudentDTO){
        return userService.addStudent(addStudentDTO);
    }
    @GetMapping("/get-all-students")
    public ResponseEntity getAllStudent(    ){
        return userService.getAllStudents();
    }
    @GetMapping("/get-student-by-id")
    public ResponseEntity getStudentByID(@RequestParam("userID") int userID){
        return userService.getStudentByID(userID);
    }
    @DeleteMapping("/remove-student-by-id")
    public ResponseEntity deleteStudentByID(@RequestParam("userID") int userID){
        return userService.deleteStudentByID(userID);
    }

    @PostMapping("/add-teacher")
    public ResponseEntity addTeacher(@RequestBody TeacherDTO teacherDTO){
        return userService.addTeacher(teacherDTO);
    }
    @GetMapping("/get-all-teachers")
    public ResponseEntity getAllTeachers(){
        return userService.getAllTeachers();
    }
    @GetMapping("/get-teacher-by-id")
    public ResponseEntity getTeacherByID(@RequestParam("teacherID")int teacherID){
        return userService.getTeacherByID(teacherID);
    }
    @PutMapping("/update-teacher")
    public ResponseEntity updateTeacherByID(@RequestBody TeacherDTO teacherDTO){
        return userService.updateTeacherByID(teacherDTO);
    }
    @DeleteMapping("/remove-teacher-by-id")
    public ResponseEntity removeTeacherByID(@RequestParam("teacherID")int teacherID){
        return userService.removeTeacherByID(teacherID);
    }

        @GetMapping("/get-student-full-details-by-id")
    public ResponseEntity getStudentFullDetailsByID(@RequestParam("userID") int userID){
        log.info(""+userID);
        return userService.getStudentFullDetailsByID(userID);
    }
}

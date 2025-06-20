package com.ecdms.ecdms.service;

import com.ecdms.ecdms.dto.request.AddStudentDTO;
import com.ecdms.ecdms.dto.request.TeacherDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity addStudent(AddStudentDTO addStudentDTO);

    ResponseEntity getAllStudents();

    ResponseEntity getStudentByID(int userID);

    ResponseEntity deleteStudentByID(int userID);

    ResponseEntity addTeacher(TeacherDTO teacherDTO);

    ResponseEntity getAllTeachers();

    ResponseEntity getTeacherByID(int teacherID);

    ResponseEntity updateTeacherByID(TeacherDTO teacherDTO);

    ResponseEntity removeTeacherByID(int teacherID);
}

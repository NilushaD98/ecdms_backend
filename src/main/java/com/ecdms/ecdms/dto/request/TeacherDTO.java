package com.ecdms.ecdms.dto.request;

import com.ecdms.ecdms.entity.Classroom;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeacherDTO {

    private Integer teacherID;
    private String fullName;
    private String contact;
    private String nic;
    private Date dob;
    private String email;
    private String gender;
    private String address;
    private double salary;
    private Date joiningDate;
    private List<Integer> classroomList= new ArrayList<>();
    private List<AttendanceDTO> attendanceDTOList;

    public TeacherDTO(Integer teacherID, String fullName, String contact, String nic, Date dob, String email, String gender, String address, double salary, Date joiningDate, List<Integer> classroomList) {
        this.teacherID = teacherID;
        this.fullName = fullName;
        this.contact = contact;
        this.nic = nic;
        this.dob = dob;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.salary = salary;
        this.joiningDate = joiningDate;
        this.classroomList = classroomList;
    }
}

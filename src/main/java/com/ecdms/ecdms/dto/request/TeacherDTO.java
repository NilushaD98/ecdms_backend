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
    private String gender;
    private String address;
    private double salary;
    private Date joiningDate;
    private List<Integer> classroomList= new ArrayList<>();
}

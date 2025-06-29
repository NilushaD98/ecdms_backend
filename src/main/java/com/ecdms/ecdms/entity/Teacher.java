package com.ecdms.ecdms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table(name = "teacher")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @ManyToMany(mappedBy = "teachers")
    private List<Classroom> classrooms = new ArrayList<>();
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attendance> attendances = new ArrayList<>();

    public Teacher(Integer teacherID) {
        this.teacherID = teacherID;
    }

    public Teacher(String fullName, String contact, String nic, Date dob,String email, String gender, String address, double salary, Date joiningDate, List<Classroom> classrooms) {
        this.fullName = fullName;
        this.contact = contact;
        this.nic = nic;
        this.dob = dob;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.salary = salary;
        this.joiningDate = joiningDate;
        this.classrooms = classrooms;
    }




}

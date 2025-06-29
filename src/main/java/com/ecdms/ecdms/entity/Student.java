package com.ecdms.ecdms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "student")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stuID;
    private String firstName;
    private String lastName;
    private String fullName;
    private Date dob;
    private String gender;
    private String address;
    private String program;
    private String ageCategory;

    private String fullNameParent;
    private String relationship;
    private String email;
    private String contactOne;
    private String contactTwo;
    private String status;
    @Column(length = 1000)
    private String specialNotice;
    @Column(length = 1000)
    private String allergies;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_class_id")
    private Classroom classroom;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attendance> attendances = new ArrayList<>();

    @OneToMany(mappedBy = "student",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<ExamResult> examResults = new ArrayList<>();
    public Student(String firstName, String lastName, String fullName, Date dob, String gender, String address, String program, String ageCategory, String fullNameParent, String relationship, String email, String contactOne, String contactTwo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.program = program;
        this.ageCategory = ageCategory;
        this.fullNameParent = fullNameParent;
        this.relationship = relationship;
        this.email = email;
        this.contactOne = contactOne;
        this.contactTwo = contactTwo;
    }

    public Student(Integer stuID) {
        this.stuID = stuID;
    }
}

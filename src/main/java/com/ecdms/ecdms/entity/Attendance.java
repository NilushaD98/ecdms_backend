package com.ecdms.ecdms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "attendance")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer attendanceID;
    private Date date;
    private boolean present;
    private String remarks;
    private String classType;
    @ManyToOne
    @JoinColumn(name = "studentID")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "teacherID")
    private Teacher teacher;

    public Attendance(Date date, boolean present, String remarks, Student student) {
        this.date = date;
        this.present = present;
        this.remarks = remarks;
        this.student = student;
    }

    public Attendance(Date date, boolean present, String remarks, Teacher teacher) {
        this.date = date;
        this.present = present;
        this.remarks = remarks;
        this.teacher = teacher;
    }
}

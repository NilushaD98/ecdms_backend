package com.ecdms.ecdms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "classroom")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int classID;
    private String className;
    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students;

    public Classroom(int classID) {
        this.classID = classID;
    }

    @ManyToMany
    @JoinTable(
            name="classroom_teacher",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name="teacher_id")
    )
    private List<Teacher> teachers = new ArrayList<>();

    @ManyToMany(mappedBy = "classrooms")
    private List<Announcement> announcements = new ArrayList<>();
}

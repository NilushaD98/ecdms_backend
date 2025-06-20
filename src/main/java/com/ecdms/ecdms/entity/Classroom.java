package com.ecdms.ecdms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Set<Student> students;

    @ManyToMany
    @JoinTable(
            name="classroom_teacher",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name="teacher_id")
    )
    private Set<Teacher> teachers = new HashSet<>();
}

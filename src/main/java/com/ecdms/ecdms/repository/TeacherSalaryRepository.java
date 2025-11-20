package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.TeacherSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherSalaryRepository extends JpaRepository<TeacherSalary, Integer> {
    List<TeacherSalary> findByTeacherTeacherIDOrderByYearDescMonthDesc(Integer teacherId);
}
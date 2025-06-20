package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.ExamResult;
import com.ecdms.ecdms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult,Integer> {

    @Query(value = "SELECT er FROM ExamResult er WHERE er.student.stuID=:student")
    Optional<ExamResult> findByStudent(@Param("student") Integer student);

    @Query(value = "SELECT er FROM ExamResult er WHERE er.testType.testTypeID=:testType")
    Optional<ExamResult> findByTestType(@Param("testType") Integer testType);
}

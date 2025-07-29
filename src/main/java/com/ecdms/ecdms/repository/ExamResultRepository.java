package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.ExamResult;
import com.ecdms.ecdms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult,Integer> {

    @Query(value = "SELECT er FROM ExamResult er WHERE er.student.stuID=:student")
    List<ExamResult> findByStudent(@Param("student") Integer student);

    @Query(value = "SELECT er FROM ExamResult er WHERE er.testType.testTypeID=:testType")
    Optional<ExamResult> findByTestType(@Param("testType") Integer testType);

    @Modifying
    @Query(value = "delete FROM ExamResult ex WHERE ex.testType.testTypeID=:testTypeID")
    void removeTestType(@Param("testTypeID") int testTypeID);
}

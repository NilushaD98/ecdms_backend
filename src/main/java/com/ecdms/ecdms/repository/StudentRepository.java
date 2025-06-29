package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    @Query(value = "select s from Student s WHERE s.status='ACTIVE'")
    List<Student> findAllActive();

    @Query(value = "select s from Student  s WHERE s.email=:username")
    Optional<Student> findByEmailEquals(@Param("username") String username);
}

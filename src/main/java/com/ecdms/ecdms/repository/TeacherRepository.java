package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Integer> {

    @Query(value = "SELECT t FROM Teacher t WHERE t.email=:username")
    Optional<Teacher> findByEmail(@Param("username") String username);

}

package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom,Integer> {
}

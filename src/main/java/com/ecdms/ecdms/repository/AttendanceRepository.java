package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Integer> {
}

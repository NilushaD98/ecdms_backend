package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {

    @Query(value = "SELECT a FROM Appointment a WHERE a.confirmed=false ")
    List<Appointment> getPendingAppointments();
    @Query("SELECT a FROM Appointment a WHERE a.confirmed=true ORDER BY a.scheduledTime ASC")
    List<Appointment> findAllSortedByDateAndTime();

    @Query("SELECT a FROM Appointment a WHERE a.confirmed=true AND a.studentID=:userID ORDER BY a.scheduledTime ASC ")
    List<Appointment> findAllSortedByDateAndTimeAndUser(@Param("userID") int userID);
}

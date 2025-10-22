package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Integer> {

    @Query("SELECT DISTINCT a.student.stuID FROM Attendance a " +
            "WHERE a.student IS NOT NULL AND DATE(a.date) = DATE(:targetDate) AND a.classType= :type AND a.present=true")
    List<Integer> findStudentIDsByDate(@Param("targetDate") Date targetDate,@Param("type") String type);


    @Query("SELECT a FROM Attendance a " +
            "WHERE a.student.stuID=:student AND DATE(a.date) = DATE(:targetDate) AND a.classType=:type")
    Optional<Attendance> findByStudentAndDate(@Param("student")Integer student,@Param("targetDate") Date date, @Param("type") String type);


    @Query("SELECT DISTINCT a.teacher.teacherID FROM Attendance a " +
            "WHERE a.teacher IS NOT NULL AND DATE(a.date) = DATE(:targetDate) AND a.present=true")
    List<Integer> findTeacherIDsByDate(@Param("targetDate") Date date);

    @Query("SELECT a FROM Attendance a " +
            "WHERE a.teacher.teacherID=:teacher AND DATE(a.date) = DATE(:targetDate)")
    Optional<Attendance> findByTeacherAndDate(@Param("teacher")Integer teacher,@Param("targetDate") Date date);
    @Query("SELECT a FROM Attendance a " +
            "WHERE a.student.stuID=:student")
    List<Attendance> findByUser(@Param("student")Integer student);

    @Query("SELECT DISTINCT a.student.stuID FROM Attendance a " +
            "WHERE a.student IS NOT NULL AND DATE(a.date) = DATE(:targetDate) AND a.classType= :type AND a.present=false")
    List<Integer> findStudentIDsByDateAbsent(@Param("targetDate")Date attendanceDate,@Param("type") String dayCare);
}

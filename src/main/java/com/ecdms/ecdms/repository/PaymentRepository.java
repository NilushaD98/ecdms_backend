package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer> {

    @Query("SELECT p FROM Payment p WHERE p.dueDate BETWEEN :start AND :end AND p.paid = false")
    List<Payment> findByDueDateBetween(@Param("start") Date start, @Param("end") Date end);

    boolean existsByUserIdAndDueDateAndPaidTrue(Integer userId, Date dueDate);

    @Query("SELECT p FROM Payment p WHERE p.userId=:studentID")
    List<Payment> findByUserID( @Param("studentID")Integer studentID);
}

package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPRepository extends JpaRepository<OTP,Integer> {

}

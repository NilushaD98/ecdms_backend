package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.OTPConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPConfigurationRepository extends JpaRepository<OTPConfiguration,Integer> {
}

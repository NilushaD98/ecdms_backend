package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.TestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface TestTypeRepository extends JpaRepository<TestType,Integer> {
}

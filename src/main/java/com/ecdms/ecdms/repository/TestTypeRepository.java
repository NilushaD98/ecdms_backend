package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.TestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface TestTypeRepository extends JpaRepository<TestType,Integer> {

    @Query(value = "SELECT t FROM TestType t WHERE t.testClass=:testClass")
    List<TestType> findAllTestClassEquals(@Param("testClass") int testClass);
}

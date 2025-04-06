package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.TokenHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Supplier;

@Repository
public interface TokenHistoryRepository extends JpaRepository<TokenHistory,Integer> {

    @Query(value = "SELECT t FROM TokenHistory t WHERE t.tokenUuid=:uuid")
    Optional<TokenHistory> findTokenHistoryByTokenUUIDEquals(@Param("uuid") String uuid);
}

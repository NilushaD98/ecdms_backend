package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,Integer> {
}

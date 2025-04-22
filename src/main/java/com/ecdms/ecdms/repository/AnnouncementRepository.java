package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement,Integer> {
}

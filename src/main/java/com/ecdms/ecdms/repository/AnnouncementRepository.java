package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement,Integer> {
    @Query(value = "SELECT a FROM Announcement a ORDER BY a.postDate DESC ")
    List<Announcement> findAllOrderByDate();
}

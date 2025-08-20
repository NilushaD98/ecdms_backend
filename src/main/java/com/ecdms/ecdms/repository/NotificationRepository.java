package com.ecdms.ecdms.repository;

import com.ecdms.ecdms.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository <Notification,Integer>{
}

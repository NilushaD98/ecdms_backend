package com.ecdms.ecdms.service.IMPL;


import com.ecdms.ecdms.entity.Notification;
import com.ecdms.ecdms.repository.NotificationRepository;
import com.ecdms.ecdms.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceIMPL implements NotificationService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final NotificationRepository notificationRepository;
    public void sendNotification(String userID, Notification notification){
        log.info("Sent Notification to {} with payload {}",userID,notification);

        simpMessagingTemplate.convertAndSendToUser(
                userID,
                "/queue/notifications",
                notification
        );
    }



}

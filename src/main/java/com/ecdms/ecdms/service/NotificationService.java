package com.ecdms.ecdms.service;

import com.ecdms.ecdms.entity.Notification;

public interface NotificationService {

    void sendNotification(String userID, Notification notification);
}

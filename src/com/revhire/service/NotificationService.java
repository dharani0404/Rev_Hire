package com.revhire.service;

import com.revhire.dao.NotificationDAO;
import com.revhire.model.Notification;

import java.time.LocalDateTime;
import java.util.List;

public class NotificationService {

    private NotificationDAO notificationDAO = new NotificationDAO();

    public void sendNotification(int userId, String message) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setMessage(message);
        n.setRead(false);
        n.setCreatedAt(LocalDateTime.now());

        notificationDAO.addNotification(n);
    }

    public List<Notification> viewNotifications(int userId) {
        return notificationDAO.getNotificationsByUser(userId);
    }

    public boolean markNotificationRead(int notificationId) {
        return notificationDAO.markAsRead(notificationId);
    }

    public boolean removeNotification(int notificationId) {
        return notificationDAO.deleteNotification(notificationId);
    }
}

package com.revhire.dao;

import com.revhire.model.Notification;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    private static final List<Notification> notifications = new ArrayList<>();
    private static int idCounter = 1;

    public void addNotification(Notification notification) {
        notification.setNotificationId(idCounter++);
        notifications.add(notification);
    }

    public List<Notification> getNotificationsByUser(int userId) {
        List<Notification> result = new ArrayList<>();
        for (Notification n : notifications) {
            if (n.getUserId() == userId) {
                result.add(n);
            }
        }
        return result;
    }

    public boolean markAsRead(int notificationId) {
        for (Notification n : notifications) {
            if (n.getNotificationId() == notificationId) {
                n.setRead(true);
                return true;
            }
        }
        return false;
    }

    public boolean deleteNotification(int notificationId) {
        return notifications.removeIf(n -> n.getNotificationId() == notificationId);
    }
}

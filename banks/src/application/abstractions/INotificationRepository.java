package application.abstractions;

import domain.models.notofications.Notification;

import java.util.HashSet;

public interface INotificationRepository {
    HashSet<Notification> getAllNotifications();

    void AddNotification(Notification notification);
}

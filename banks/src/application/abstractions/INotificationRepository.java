package application.abstractions;

import domain.models.notofications.Notification;

import java.util.HashSet;
import java.util.Set;

public interface INotificationRepository {
    Set<Notification> getAllNotifications();

    void AddNotification(Notification notification);
}

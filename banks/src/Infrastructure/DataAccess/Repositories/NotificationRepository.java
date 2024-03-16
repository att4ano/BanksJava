package Infrastructure.DataAccess.Repositories;

import application.abstractions.INotificationRepository;
import domain.models.notofications.Notification;

import java.util.HashSet;

/**
 * Репозиторий уведомлений
 */
public class NotificationRepository implements INotificationRepository {

    private final HashSet<Notification> _notifications;

    public NotificationRepository(HashSet<Notification> notifications) {
        _notifications = notifications;
    }

    /**
     * @return все уведомления
     */
    @Override
    public HashSet<Notification> getAllNotifications() {
        return _notifications;
    }

    /**
     * @param notification уведомление, которое надо добавить
     */
    @Override
    public void AddNotification(Notification notification) {
        _notifications.add(notification);
    }
}

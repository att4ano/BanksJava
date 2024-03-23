package Infrastructure.DataAccess.Repositories;

import application.abstractions.INotificationRepository;
import domain.models.notofications.Notification;

import java.util.HashSet;
import java.util.Set;

/**
 * Репозиторий уведомлений
 */
public class NotificationRepository implements INotificationRepository {

    private final Set<Notification> notifications;

    public NotificationRepository(HashSet<Notification> notifications) {
        this.notifications = notifications;
    }

    /**
     * @return все уведомления
     */
    @Override
    public Set<Notification> getAllNotifications() {
        return notifications;
    }

    /**
     * @param notification уведомление, которое надо добавить
     */
    @Override
    public void AddNotification(Notification notification) {
        notifications.add(notification);
    }
}

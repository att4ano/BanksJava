package domain.models.notofications;

import domain.models.Bank;
import domain.models.Client;
import lombok.Getter;

import java.util.UUID;

/**
 * Абстрактный класс аккаунта уведомлений
 */
@Getter
public abstract class Notification {
    protected final UUID _id;
    protected final Client _client;
    protected final Bank _bank;

    protected Notification(UUID id, Client client, Bank bank) {
        _id = id;
        _client = client;
        _bank = bank;

    }

    /**
     * @return представление уведомления ввиде строки
     */
    public abstract String toString();
}

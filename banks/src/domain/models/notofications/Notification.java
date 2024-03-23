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
    protected final UUID id;
    protected final Client client;
    protected final Bank bank;

    protected Notification(UUID id, Client client, Bank bank) {
        this.id = id;
        this.client = client;
        this.bank = bank;

    }

    /**
     * @return представление уведомления ввиде строки
     */
    public abstract String toString();
}

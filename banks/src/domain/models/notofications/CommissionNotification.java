package domain.models.notofications;

import domain.models.Bank;
import domain.models.Client;

import java.util.UUID;

/**
 * класс уведомлений изменений по комиссии
 */
public class CommissionNotification extends Notification{
    public CommissionNotification(Client client, Bank bank) {
        super(UUID.randomUUID(), client, bank);
    }

    /**
     * @return представление ввидк строки
     */
    @Override
    public String toString() {
        return bank.getName() + " has changed his credit commission!";
    }
}

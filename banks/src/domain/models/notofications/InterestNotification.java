package domain.models.notofications;

import domain.models.Bank;
import domain.models.Client;

import java.util.UUID;

/**
 * Уведомление по изменению проуентов для выплат
 */
public class InterestNotification extends Notification{

    public InterestNotification(Client client, Bank bank) {
        super(UUID.randomUUID(), client, bank);
    }

    /**
     * @return Представление ввиде строки
     */
    @Override
    public String toString() {
        return _bank.get_name() + " has changed his deposit interest!";
    }
}

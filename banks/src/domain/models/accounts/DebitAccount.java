package domain.models.accounts;

import domain.models.Bank;
import domain.models.Client;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Дебетовый аккаунт
 */
@SuperBuilder
public class DebitAccount extends Account {
    public DebitAccount(UUID accountId, Bank bank, Client client) {
        super(accountId, bank, client);
    }

    /**
     * @param moneyAmount количество денег, которые надо добавить
     */
    @Override
    public void addMoney(BigDecimal moneyAmount) {
       _moneyAmount = _moneyAmount.add(moneyAmount);
    }

    /**
     * @param moneyAmount количество денег
     */
    @Override
    public void withdrawMoney(BigDecimal moneyAmount) {
        if (_moneyAmount.compareTo(moneyAmount) >= 0)
            _moneyAmount = _moneyAmount.subtract(moneyAmount);
    }

    /**
     * @return представление ввиде строки
     */
    @Override
    public String toString() {
        return "Id: " + _id.toString() + " | " + "Type: Debit" + " | " + "Bank name: " + _bank.get_name() + " | " + "Money amount: " + _moneyAmount.toString();
    }
}

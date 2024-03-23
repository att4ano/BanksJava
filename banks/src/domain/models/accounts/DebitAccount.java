package domain.models.accounts;

import domain.exceptions.MoneyOperationException;
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
     * @param newMoneyAmount количество денег, которые надо добавить
     */
    @Override
    public void addMoney(BigDecimal newMoneyAmount) {
       moneyAmount = moneyAmount.add(newMoneyAmount);
    }

    /**
     * @param newMoneyAmount количество денег
     */
    @Override
    public void withdrawMoney(BigDecimal newMoneyAmount) throws MoneyOperationException {
        if (moneyAmount.compareTo(newMoneyAmount) >= 0) {
            moneyAmount = moneyAmount.subtract(newMoneyAmount);
        } else {
            throw MoneyOperationException.notEnoughMoney();
        }
    }

    /**
     * @return представление ввиде строки
     */
    @Override
    public String toString() {
        return "Id: " + id.toString() + " | " + "Type: Debit" + " | " + "Bank name: " + bank.getName() + " | " + "Money amount: " + moneyAmount.toString();
    }
}

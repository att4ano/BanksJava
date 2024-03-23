package domain.models.accounts;

import domain.models.Bank;
import domain.models.Client;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Кредитный аккаунт
 */
@SuperBuilder
public class CreditAccount extends Account {
    public CreditAccount(UUID accountId, Bank bank, Client client, BigDecimal moneyAmount) {
        super(accountId, bank, client);
        this.moneyAmount = moneyAmount;
    }

    /**
     * @param newMoneyAmount количество денег, которые надо добавить
     */
    @Override
    public void addMoney(@NotNull BigDecimal newMoneyAmount) {
        if (moneyAmount.compareTo(bank.getLimit()) >= 0) {
            moneyAmount = moneyAmount.add(newMoneyAmount);
        } else {
            moneyAmount = moneyAmount.add(newMoneyAmount.multiply(BigDecimal.valueOf(bank.getCommission() + 1)));
        }
    }

    /**
     * @param newMoneyAmount количество денег
     */
    @Override
    public void withdrawMoney(@NotNull BigDecimal newMoneyAmount) {
        if (moneyAmount.compareTo(bank.getLimit()) >= 0) {
            moneyAmount = moneyAmount.subtract(newMoneyAmount);
        } else {
            moneyAmount = moneyAmount.subtract(newMoneyAmount.multiply(BigDecimal.valueOf(bank.getCommission() + 1)));
        }
    }

    /**
     * @return представлние ввиде строки
     */
    @Override
    public String toString() {
        return "Id: " + id.toString() + " | " + "Type: Credit" + " | " + "Bank name: " + bank.getName() + " | " + "Money amount: " + moneyAmount.toString();
    }
}

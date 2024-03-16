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
    }

    /**
     * @param moneyAmount количество денег, которые надо добавить
     */
    @Override
    public void addMoney(@NotNull BigDecimal moneyAmount) {
        if (moneyAmount.compareTo(_bank.get_limit()) >= 0) {
            _moneyAmount = _moneyAmount.add(moneyAmount);
        } else {
            _moneyAmount = _moneyAmount.add(moneyAmount.multiply(BigDecimal.valueOf(_bank.get_commission() + 1)));
        }
    }

    /**
     * @param moneyAmount количество денег
     */
    @Override
    public void withdrawMoney(@NotNull BigDecimal moneyAmount) {
        if (moneyAmount.compareTo(_bank.get_limit()) >= 0) {
            _moneyAmount = _moneyAmount.subtract(moneyAmount);
        } else {
            _moneyAmount = _moneyAmount.subtract(moneyAmount.multiply(BigDecimal.valueOf(_bank.get_commission() + 1)));
        }
    }

    /**
     * @return представлние ввиде строки
     */
    @Override
    public String toString() {
        return "Id: " + _id.toString() + " | " + "Type: Credit" + " | " + "Bank name: " + _bank.get_name() + " | " + "Money amount: " + _moneyAmount.toString();
    }
}

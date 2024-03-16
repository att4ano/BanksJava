package domain.models.accounts;

import domain.models.Bank;
import domain.models.Client;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Депозитный аккаунт
 */
@SuperBuilder
public class DepositAccount extends Account {
    @Getter
    @NotNull
    private final Integer _term;

    public DepositAccount(UUID accountId, Bank bank, Client client, Integer term) {
        super(accountId, bank, client);
        _term = term;
    }

    /**
     * @param moneyAmount количество денег, которые надо добавить
     */
    @Override
    public void addMoney(BigDecimal moneyAmount) {
        if (_term == 0)
            _moneyAmount = _moneyAmount.add(moneyAmount);
    }

    /**
     * @param moneyAmount количество денег
     */
    @Override
    public void withdrawMoney(BigDecimal moneyAmount) {
        if (_term == 0) {
            if (_moneyAmount.compareTo(moneyAmount) > 0)
                _moneyAmount = _moneyAmount.subtract(moneyAmount);
        }
    }

    /**
     * @return вернуть представлени ввиде строки
     */
    @Override
    public String toString() {
        return "Id: " + _id.toString() + " | " + "Type: Deposit" + " | " + "Bank name: " + _bank.get_name() + " | " + "Money amount: " + _moneyAmount.toString() + " | " + "Term: " + _term.toString();
    }

    /**
     * Сделать выплату по аккаунту
     */
    public void makePayment() {
        _moneyAmount = _moneyAmount.multiply(BigDecimal.valueOf(_bank.get_interest() + 1));
    }
}

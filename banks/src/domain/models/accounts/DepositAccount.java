package domain.models.accounts;

import domain.exceptions.DepositAccountException;
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
@Getter
@SuperBuilder
public class DepositAccount extends Account {
    @NotNull
    private final Integer term;

    public DepositAccount(UUID accountId, Bank bank, Client client, @NotNull Integer term) {
        super(accountId, bank, client);
        this.term = term;
    }

    /**
     * @param newMoneyAmount количество денег, которые надо добавить
     */
    @Override
    public void addMoney(BigDecimal newMoneyAmount) throws DepositAccountException {
        if (term == 0) {
            moneyAmount = moneyAmount.add(newMoneyAmount);
        } else {
            throw DepositAccountException.termIsNotOver();
        }
    }

    /**
     * @param newMoneyAmount количество денег
     */
    @Override
    public void withdrawMoney(BigDecimal newMoneyAmount) throws DepositAccountException {
        if (term == 0) {
            if (moneyAmount.compareTo(newMoneyAmount) > 0)
                moneyAmount = moneyAmount.subtract(newMoneyAmount);
        } else {
            throw DepositAccountException.termIsNotOver();
        }
    }

    /**
     * Сделать выплату по аккаунту
     */
    public void makePayment() {
        moneyAmount = moneyAmount.multiply(BigDecimal.valueOf(bank.getInterest() + 1));
    }

    /**
     * @return вернуть представлени ввиде строки
     */
    @Override
    public String toString() {
        return "Id: " + id.toString() + " | " + "Type: Deposit" + " | " + "Bank name: " + bank.getName() + " | " + "Money amount: " + moneyAmount.toString() + " | " + "Term: " + term;
    }
}

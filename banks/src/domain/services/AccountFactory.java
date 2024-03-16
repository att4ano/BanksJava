package domain.services;

import domain.interfaces.IAccountFactory;
import domain.models.Bank;
import domain.models.Client;
import domain.models.accounts.Account;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * класс для создания аккаунтов
 */
public class AccountFactory implements IAccountFactory {
    /**
     * @param client клиент для которого создается
     * @param bank банк в котором моздается
     * @return сам аккаунт
     */
    @Override
    public Account createDebitAccount(@NotNull Client client, Bank bank) {
        return client.createDebitAccount(bank);
    }

    /**
     * @param client клиент для которого создается
     * @param bank банк в котором моздается
     * @param moneyAmount количество денег, которые кладутся на счет
     * @return сам аккаунт
     */
    @Override
    public Account createCreditAccount(@NotNull Client client, Bank bank, BigDecimal moneyAmount) {
        return client.createCreditAccount(bank, moneyAmount);
    }

    /**
     * @param client клиент для которого создается
     * @param bank банк в котором моздается
     * @param moneyAmount количество денег, которые кладутся на счет
     * @param term срок на время которого кладутся деньги
     * @return сам аккаунт
     */
    @Override
    public Account createDepositAccount(@NotNull Client client, Bank bank, Integer term, BigDecimal moneyAmount) {
        return client.createDepositAccount(bank, term, moneyAmount);
    }
}

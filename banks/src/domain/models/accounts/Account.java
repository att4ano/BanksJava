package domain.models.accounts;

import domain.exceptions.DepositAccountException;
import domain.exceptions.DomainException;
import domain.interfaces.Transaction;
import domain.models.Bank;
import domain.models.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;

/**
 * Абстрактный класс счета, который показывает поля поумолчанию для него
 */
@SuperBuilder
@Getter
@AllArgsConstructor
public abstract class Account
{
    protected final UUID id;
    protected final Bank bank;
    protected final Client client;
    protected BigDecimal moneyAmount;
    protected HashSet<Transaction> operations = new HashSet<>();

    public Account(UUID accountId, Bank bank, Client client)
    {
        this.id = accountId;
        this.bank = bank;
        this.client = client;
        this.moneyAmount = new BigDecimal(0);
    }

    /**
     * @param moneyAmount количество денег, которые надо добавить
     */
    public abstract void addMoney(BigDecimal moneyAmount) throws DomainException;

    /**
     * @param moneyAmount количество денег
     */
    public abstract void withdrawMoney(BigDecimal moneyAmount) throws DomainException;

    /**
     * @return представление ввиде строки
     */
    public abstract String toString();
}

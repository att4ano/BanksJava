package domain.models.accounts;

import domain.interfaces.Transaction;
import domain.models.Bank;
import domain.models.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    protected final UUID _id;
    protected final Bank _bank;
    protected final Client _client;
    protected BigDecimal _moneyAmount;
    protected HashSet<Transaction> _operations = new HashSet<>();

    public Account(UUID accountId, Bank bank, Client client)
    {
        _id = accountId;
        _bank = bank;
        _client = client;
        _moneyAmount = new BigDecimal(0);
    }

    /**
     * @param moneyAmount количество денег, которые надо добавить
     */
    public abstract void addMoney(BigDecimal moneyAmount);

    /**
     * @param moneyAmount количество денег
     */
    public abstract void withdrawMoney(BigDecimal moneyAmount);

    /**
     * @return представление ввиде строки
     */
    public abstract String toString();
}

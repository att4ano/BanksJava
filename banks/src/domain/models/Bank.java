package domain.models;

import domain.interfaces.ISubscriber;
import domain.models.accounts.Account;
import domain.models.accounts.CreditAccount;
import domain.models.accounts.DebitAccount;
import domain.models.accounts.DepositAccount;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;

/**
 * Класс банка, где прописаны бизнес-логика его работы
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Bank implements ISubscriber {
    private final UUID _id;
    private final String _name;
    private double _interest;
    private double _commission;
    private BigDecimal _limit;
    private HashSet<Account> _accounts = new HashSet<>();

    public Bank(UUID id, String name, double interest, double commission, BigDecimal limit) {
        _id = id;
        _name = name;
        _interest = interest;
        _commission = commission;
        _limit = limit;
    }

    /**
     * выплата по депозитным счетам этого банка
     */
    @Override
    public void update() {
        for (var account : _accounts) {
            if (account instanceof DepositAccount depositAccount) {
                depositAccount.makePayment();
            }
        }
    }

    /**
     * @param fromAccount аккаунт с которого переводят
     * @param toAccount аккаунт куда переводят деньги
     * @param moneyAmount количество денег
     */
    public void transferMoney(@NotNull Account fromAccount, @NotNull Account toAccount, BigDecimal moneyAmount) {
        fromAccount.withdrawMoney(moneyAmount);
        toAccount.addMoney(moneyAmount);
    }

    /**
     * @param account какой аккаунт пополняется
     * @param moneyAmount количество денег на которое поплняется
     */
    public void addMoney(@NotNull Account account, BigDecimal moneyAmount) {
        account.addMoney(moneyAmount);
    }

    /**
     * @param account аккаунт с которого снимаются деньги
     * @param moneyAmount количсество денег, которыне снимаются
     */
    public void withdrawMoney(@NotNull Account account, BigDecimal moneyAmount) {
        account.withdrawMoney(moneyAmount);
    }

    /**
     * @param client клиент для которого делатеся аккаунт
     * @return созданный аккаунт
     */
    public Account createDebitAccount(Client client) {
        Account account = DebitAccount.builder()
                ._id(UUID.randomUUID())
                ._bank(this)
                ._client(client)
                ._moneyAmount(BigDecimal.valueOf(0))
                .build();
        _accounts.add(account);
        client.get_accounts().add(account);
        return account;
    }

    /**
     * @param client клиент для которого делатеся аккаунт
     * @param moneyAmount количество денег, которое кладется
     * @return созданный аккаунт
     */
    public Account createCreditAccount(Client client, BigDecimal moneyAmount) {
        Account account = CreditAccount.builder()
                ._id(UUID.randomUUID())
                ._bank(this)
                ._client(client)
                ._moneyAmount(moneyAmount)
                .build();
        _accounts.add(account);
        return account;
    }

    /**
     * @param client клиент для которого делатеся аккаунт
     * @param term срок на который приходятся выплаты по счету
     * @param moneyAmount количество денег
     * @return созданный аккаунт
     */
    public Account createDepositAccount(Client client, Integer term, BigDecimal moneyAmount) {
        Account account = DepositAccount.builder()
                ._id(UUID.randomUUID())
                ._bank(this)
                ._client(client)
                ._term(term)
                ._moneyAmount(moneyAmount)
                .build();
        _accounts.add(account);
        return account;
    }

    /**
     * @return представление банка ввиде строки
     */
    public String toString() {
        return "Id: " + _id.toString() + " | " + "name: " + _name + " | " + "Interest: " + _interest + " | " + "Commission: " + _commission + " | " + "Limit: " + _limit;
    }

}

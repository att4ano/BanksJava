package domain.models;

import domain.exceptions.DomainException;
import domain.interfaces.ISubscriber;
import domain.models.accounts.Account;
import domain.models.accounts.CreditAccount;
import domain.models.accounts.DebitAccount;
import domain.models.accounts.DepositAccount;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Класс банка, где прописаны бизнес-логика его работы
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Bank implements ISubscriber {
    private final UUID id;
    private String name;
    private double interest;
    private double commission;
    private BigDecimal limit;
    private Set<Account> accounts = new HashSet<>();
    private Set<Client> subscribers = new HashSet<>();

    public Bank(UUID id, String name, double interest, double commission, BigDecimal limit) {
        this.id = id;
        this.name = name;
        this.interest = interest;
        this.commission = commission;
        this.limit = limit;
    }

    /**
     * выплата по депозитным счетам этого банка
     */
    @Override
    public void update() {
        for (var account : accounts) {
            if (account instanceof DepositAccount depositAccount) {
                depositAccount.makePayment();
            }
        }
    }

    public void subscribe(Client client) {
        subscribers.add(client);
    }

    /**
     * @param fromAccount аккаунт с которого переводят
     * @param toAccount аккаунт куда переводят деньги
     * @param moneyAmount количество денег
     */
    public void transferMoney(@NotNull Account fromAccount, @NotNull Account toAccount, BigDecimal moneyAmount) {
        try {
            fromAccount.withdrawMoney(moneyAmount);
            toAccount.addMoney(moneyAmount);
        } catch (DomainException exception) {
            System.out.println(exception.toString());
        }

    }

    /**
     * @param account какой аккаунт пополняется
     * @param moneyAmount количество денег на которое поплняется
     */
    public void addMoney(@NotNull Account account, BigDecimal moneyAmount) {
        try {
            account.addMoney(moneyAmount);
        } catch (DomainException exception) {
            System.out.println(exception.toString());
        }
    }

    /**
     * @param account аккаунт с которого снимаются деньги
     * @param moneyAmount количсество денег, которыне снимаются
     */
    public void withdrawMoney(@NotNull Account account, BigDecimal moneyAmount) {
        try {
            account.withdrawMoney(moneyAmount);
        } catch (DomainException exception) {
            System.out.println(exception.toString());
        }
    }

    /**
     * @param client клиент для которого делатеся аккаунт
     * @return созданный аккаунт
     */
    public Account createDebitAccount(Client client) {
        Account account = DebitAccount.builder()
                .id(UUID.randomUUID())
                .bank(this)
                .client(client)
                .moneyAmount(BigDecimal.valueOf(0))
                .build();
        accounts.add(account);
        client.getAccounts().add(account);
        return account;
    }

    /**
     * @param client клиент для которого делатеся аккаунт
     * @param moneyAmount количество денег, которое кладется
     * @return созданный аккаунт
     */
    public Account createCreditAccount(Client client, BigDecimal moneyAmount) {
        Account account = CreditAccount.builder()
                .id(UUID.randomUUID())
                .bank(this)
                .client(client)
                .moneyAmount(moneyAmount)
                .build();
        accounts.add(account);
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
                .id(UUID.randomUUID())
                .bank(this)
                .client(client)
                .term(term)
                .moneyAmount(moneyAmount)
                .build();
        accounts.add(account);
        return account;
    }

    /**
     * @return представление банка ввиде строки
     */
    public String toString() {
        return "Id: " + id.toString() + " | " + "name: " + name + " | " + "Interest: " + interest + " | " + "Commission: " + commission + " | " + "Limit: " + limit;
    }

}

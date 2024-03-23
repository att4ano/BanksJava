package domain.models;

import domain.models.accounts.Account;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;

/**
 * класс клиента, который описвыает всю бизнес логику
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
public class Client {
    private final UUID id;
    private String name;
    private String surname;

    @Setter
    @Nullable
    private String address;

    @Setter
    @Nullable
    private String passportData;
    private HashSet<Account> accounts = new HashSet<>();

    public Client(UUID id, String name, String surname, @Nullable String address, @Nullable String passportData) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.passportData = passportData;
    }

    /**
     * @param fromAccount аккаунт, с которого переводят
     * @param toAccount аккаунт, куда переводят
     * @param moneyAmount количество, денег которые переводят
     */
    public void transferMoney(@NotNull Account fromAccount, @NotNull Account toAccount, BigDecimal moneyAmount) {
        if (fromAccount.getBank().equals(toAccount.getBank())) {
            if (accounts.contains(fromAccount))
                fromAccount.getBank().transferMoney(fromAccount, toAccount, moneyAmount);
        } else {
            CentralBank centralBank = new CentralBank();
            if (accounts.contains(fromAccount)) {
                centralBank.transferMoney(fromAccount, toAccount, moneyAmount);
            }
        }
    }

    /**
     * @param account какой аккаунт пополняется
     * @param moneyAmount количество денег на которое поплняется
     */
    public void addMoney(Account account, BigDecimal moneyAmount) {
        if (accounts.contains(account))
            account.getBank().addMoney(account, moneyAmount);
    }

    /**
     * @param account аккаунт с которого снимаются деньги
     * @param moneyAmount количсество денег, которыне снимаются
     */
    public void withdrawMoney(Account account, BigDecimal moneyAmount) {
        if (accounts.contains(account))
            account.getBank().withdrawMoney(account, moneyAmount);
    }

    /**
     * @param bank банк в котором создается аккаунт
     * @return созданный аккаунт
     */
    public Account createDebitAccount(@NotNull Bank bank) {
        Account currentAccount = bank.createDebitAccount(this);
        accounts.add(currentAccount);

        return currentAccount;
    }

    /**
     * @param bank банк в котором создается аккаунт
     * @param moneyAmount количетство денег, которое кладется на счет
     * @return созданный аккаунт
     */
    public Account createDepositAccount(@NotNull Bank bank, Integer term, BigDecimal moneyAmount) {
        Account currentAccount = bank.createDepositAccount(this, term, moneyAmount);
        accounts.add(currentAccount);

        return currentAccount;
    }

    /**
     /**
     * @param bank банк в котором создается аккаунт
     * @param moneyAmount количетство денег, которое кладется на счет
     * @return созданный аккаунт
     */
    public Account createCreditAccount(@NotNull Bank bank, BigDecimal moneyAmount) {
        Account currentAccount = bank.createCreditAccount(this, moneyAmount);
        accounts.add(currentAccount);

        return currentAccount;
    }

    /**
     * @return представление в виде строки
     */
    public String toString() {
        return "Id: " + id.toString() + " | "
                + "Name: " + name + " | "
                + "Surname: " + surname + " | "
                + "Address: " + ((address != null) ? address : "NONE") + " | "
                + "PassportData " + ((passportData != null) ? passportData : "NONE");
    }
}

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
@AllArgsConstructor
public class Client {
    private final UUID _id;
    private final String _name;
    private final String _surname;
    @Setter
    @Nullable
    private String _address;
    @Setter
    @Nullable
    private String _passportData;
    private HashSet<Account> _accounts = new HashSet<>();
    public Client(UUID id, String name, String surname, @Nullable String address, @Nullable String passportData) {
        _id = id;
        _name = name;
        _surname = surname;
        _address = address;
        _passportData = passportData;
    }

    /**
     * @param fromAccount аккаунт, с которого переводят
     * @param toAccount аккаунт, куда переводят
     * @param moneyAmount количество, денег которые переводят
     */
    public void transferMoney(@NotNull Account fromAccount, @NotNull Account toAccount, BigDecimal moneyAmount) {
        if (fromAccount.get_bank().equals(toAccount.get_bank())) {
            if (_accounts.contains(fromAccount))
                fromAccount.get_bank().transferMoney(fromAccount, toAccount, moneyAmount);
        } else {
            CentralBank centralBank = new CentralBank();
            if (_accounts.contains(fromAccount)) {
                centralBank.transferMoney(fromAccount, toAccount, moneyAmount);
            }
        }
    }

    /**
     * @param account какой аккаунт пополняется
     * @param moneyAmount количество денег на которое поплняется
     */
    public void addMoney(Account account, BigDecimal moneyAmount) {
        if (_accounts.contains(account))
            account.get_bank().addMoney(account, moneyAmount);
    }

    /**
     * @param account аккаунт с которого снимаются деньги
     * @param moneyAmount количсество денег, которыне снимаются
     */
    public void withdrawMoney(Account account, BigDecimal moneyAmount) {
        if (_accounts.contains(account))
            account.get_bank().withdrawMoney(account, moneyAmount);
    }

    /**
     * @param bank банк в котором создается аккаунт
     * @return созданный аккаунт
     */
    public Account createDebitAccount(@NotNull Bank bank) {
        Account currentAccount = bank.createDebitAccount(this);
        _accounts.add(currentAccount);

        return currentAccount;
    }

    /**
     * @param bank банк в котором создается аккаунт
     * @param moneyAmount количетство денег, которое кладется на счет
     * @return созданный аккаунт
     */
    public Account createDepositAccount(@NotNull Bank bank, Integer term, BigDecimal moneyAmount) {
        Account currentAccount = bank.createDepositAccount(this, term, moneyAmount);
        _accounts.add(currentAccount);

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
        _accounts.add(currentAccount);

        return currentAccount;
    }

    /**
     * @return представление в виде строки
     */
    public String toString() {
        return "Id: " + _id.toString() + " | "
                + "Name: " + _name + " | "
                + "Surname: " + _surname + " | "
                + "Address: " + ((_address != null) ? _address : "NONE") + " | "
                + "PassportData " + ((_passportData != null) ? _passportData : "NONE");
    }
}

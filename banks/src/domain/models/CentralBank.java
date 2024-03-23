package domain.models;

import domain.exceptions.DomainException;
import domain.models.accounts.Account;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.Set;
import java.util.HashSet;
import java.util.UUID;

/**
 * Класс центрального банка, где описаны все его бизнес правила
 */
public class CentralBank
{
    private final Set<Bank> _banks = new HashSet<>();

    public CentralBank() { }

    /**
     * @param fromAccount аккаунт, с которого переводят
     * @param toAccount аккаунт, куда переводят
     * @param moneyAmount количество, денег которые переводят
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
     * @param bank "подписать" банк на центральный банк
     */
    public void subscribe(Bank bank) {
        _banks.add(bank);
    }

    /**
     * @param bank "отписать" банк от центрального банка
     */
    public void unsubscribe(Bank bank) {
        _banks.remove(bank);
    }

    /**
     * сказать банку, чтобы он сделал выплату по счету
     */
    public void makePayment() {
        for (var bank : _banks) {
            bank.update();
        }
    }

    /**
     * @param name имя клиента
     * @param surname фамилия клиента
     * @param address адрес клиента
     * @param passportData пасспортные данные клиента
     * @return сам клиент
     */
    public Client createNewClient(String name, String surname, @Nullable String address, @Nullable String passportData) {
        return Client.builder()
                .id(UUID.randomUUID())
                .name(name)
                .surname(surname)
                .address(address)
                .passportData(passportData)
                .accounts(new HashSet<>())
                .build();
    }

    /**
     * @param name имя клиента
     * @param interest проценты
     * @param commission коммиссия
     * @param limit лимит
     * @return созданный банк
     */
    public Bank createNewBank(String name, double interest, double commission, BigDecimal limit) {
        Bank bank =  Bank.builder()
                .id(UUID.randomUUID())
                .name(name)
                .interest(interest)
                .commission(commission)
                .limit(limit)
                .accounts(new HashSet<>())
                .subscribers(new HashSet<>())
                .build();
        subscribe(bank);
        return bank;
    }
}

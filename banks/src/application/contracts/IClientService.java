package application.contracts;

import application.cotracts.LoginResult;
import application.result.ServiceResult;
import domain.models.accounts.Account;
import domain.models.notofications.Notification;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public interface IClientService {
    LoginResult login(String name, String surname);
    ServiceResult logout();
    ServiceResult addMoney(UUID accountId, BigDecimal moneyAmount);
    ServiceResult withdrawMoney(UUID accountId, BigDecimal moneyAmount);
    ServiceResult transferMoney(UUID fromId, UUID toId, BigDecimal moneyAmount);
    ServiceResult createDebitAccount(String bankName);
    ServiceResult createDepositAccount(String bankName, Integer term, BigDecimal moneyAmount);
    ServiceResult createCreditAccount(String bankName, BigDecimal moneyAmount);
    ServiceResult updatePassportData(String passportData);
    ServiceResult updateAddress(String address);
    @Nullable List<Notification> checkClientNotifications();
    @Nullable List<Account> checkClientAccounts();
    BigDecimal accelerateTime(LocalDate date, UUID accountId);
}

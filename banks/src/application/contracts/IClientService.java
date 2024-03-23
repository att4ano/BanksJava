package application.contracts;

import application.result.ServiceResult;
import application.result.LoginResult;
import domain.exceptions.NotFoundException;
import domain.models.accounts.Account;
import domain.models.notofications.Notification;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IClientService {
    LoginResult login(String name, String surname);
    ServiceResult logout();
    ServiceResult addMoney(UUID accountId, BigDecimal moneyAmount) throws NotFoundException;
    ServiceResult withdrawMoney(UUID accountId, BigDecimal moneyAmount) throws NotFoundException;
    ServiceResult transferMoney(UUID fromId, UUID toId, BigDecimal moneyAmount) throws NotFoundException;
    ServiceResult createDebitAccount(String bankName);
    ServiceResult createDepositAccount(String bankName, Integer term, BigDecimal moneyAmount);
    ServiceResult createCreditAccount(String bankName, BigDecimal moneyAmount);
    ServiceResult updatePassportData(String passportData);
    ServiceResult updateAddress(String address);
    @Nullable List<Notification> checkClientNotifications();
    @Nullable List<Account> checkClientAccounts();
    BigDecimal accelerateTime(LocalDate date, UUID accountId) throws NotFoundException;
    ServiceResult subscribe(String bankName) throws NotFoundException;
}

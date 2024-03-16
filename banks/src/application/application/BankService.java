package application.application;

import application.abstractions.IAccountRepository;
import application.abstractions.IBankRepository;
import application.abstractions.INotificationRepository;
import application.contracts.IBankService;
import application.contracts.ICurrentUserManager;
import application.cotracts.LoginResult;
import application.result.ServiceResult;
import domain.models.Bank;
import domain.models.accounts.Account;
import domain.models.accounts.CreditAccount;
import domain.models.accounts.DepositAccount;
import domain.models.notofications.CommissionNotification;
import domain.models.notofications.InterestNotification;

import java.util.List;

/**
 * Application-service для банков
 */
public class BankService implements IBankService {
    private final IBankRepository _bankRepository;
    private final IAccountRepository _accountRepository;
    private final INotificationRepository _notificationRepository;
    private final ICurrentUserManager _currentUserManager;

    public BankService(IBankRepository bankRepository, IAccountRepository accountRepository, INotificationRepository notificationRepository, ICurrentUserManager currentUserManager) {
        _bankRepository = bankRepository;
        _accountRepository = accountRepository;
        _notificationRepository = notificationRepository;
        _currentUserManager = currentUserManager;
        _currentUserManager.setCurrentSession(new CurrentSession.UnauthorizedSession());
    }

    /**
     * @param bankName название банка в который входим
     * @return результат попытки входа
     */
    @Override
    public LoginResult login(String bankName) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.UnauthorizedSession))
            return new LoginResult.Failure();

        Bank bank = _bankRepository.getAllBanks()
                .stream()
                .filter(bank1 -> bank1.get_name().equals(bankName))
                .findFirst()
                .orElse(null);

        if (bank == null)
            return new LoginResult.Failure();

        _currentUserManager.setCurrentSession(new CurrentSession.BankSession(bank));
        return new LoginResult.Success();
    }

    /**
     * метод для выхода из аккаунта конкретного банка
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult logout() {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.BankSession))
            return new ServiceResult.Failure("You need to be logged in");

        _currentUserManager.setCurrentSession(new CurrentSession.UnauthorizedSession());
        return new ServiceResult.Success("Success");
    }

    /**
     * @param interest новые проценты
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult updateInterest(double interest) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.BankSession bankSession))
            return new ServiceResult.Failure("You need to be logged in");

        bankSession.get_bank().set_interest(interest);
        List<Account> accounts = _accountRepository.getAllAccounts()
                .stream()
                .filter(account -> account.get_bank().equals(bankSession.get_bank()) && account instanceof DepositAccount)
                .toList();

        for (var account : accounts) {
            _notificationRepository.AddNotification(new InterestNotification(account.get_client(), bankSession.get_bank()));
        }

        return new ServiceResult.Success("Success");
    }

    /**
     * @param commission новая комиссия
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult updateCommission(double commission) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.BankSession bankSession))
            return new ServiceResult.Failure("You need to be logged in");

        bankSession.get_bank().set_commission(commission);
        List<Account> accounts = _accountRepository.getAllAccounts()
                .stream()
                .filter(account -> account.get_bank().equals(bankSession.get_bank()) && account instanceof CreditAccount)
                .toList();

        for (var account : accounts) {
            _notificationRepository.AddNotification(new CommissionNotification(account.get_client(), bankSession.get_bank()));
        }

        return new ServiceResult.Success("Success");
    }


}

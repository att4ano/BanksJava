package application.application;

import application.abstractions.IAccountRepository;
import application.abstractions.IBankRepository;
import application.abstractions.INotificationRepository;
import application.contracts.IBankService;
import application.contracts.ICurrentUserManager;
import application.result.LoginResult;
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
    private final IBankRepository bankRepository;
    private final INotificationRepository notificationRepository;
    private final ICurrentUserManager currentUserManager;

    public BankService(IBankRepository bankRepository, IAccountRepository accountRepository, INotificationRepository notificationRepository, ICurrentUserManager currentUserManager) {
        this.bankRepository = bankRepository;
        this.notificationRepository = notificationRepository;
        this.currentUserManager = currentUserManager;
        this.currentUserManager.setCurrentSession(new CurrentSession.UnauthorizedSession());
    }

    /**
     * @param bankName название банка в который входим
     * @return результат попытки входа
     */
    @Override
    public LoginResult login(String bankName) {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.UnauthorizedSession))
            return LoginResult.FAILURE;

        Bank bank = bankRepository.getAllBanks()
                .stream()
                .filter(bank1 -> bank1.getName().equals(bankName))
                .findFirst()
                .orElse(null);

        if (bank == null)
            return LoginResult.NOT_FOUND;

        currentUserManager.setCurrentSession(new CurrentSession.BankSession(bank));
        return LoginResult.SUCCESS;
    }

    /**
     * метод для выхода из аккаунта конкретного банка
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult logout() {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.BankSession))
            return new ServiceResult.Failure("You need to be logged in");

        currentUserManager.setCurrentSession(new CurrentSession.UnauthorizedSession());
        return new ServiceResult.Success("Success");
    }

    /**
     * @param interest новые проценты
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult updateInterest(double interest) {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.BankSession bankSession))
            return new ServiceResult.Failure("You need to be logged in");

        bankSession.getBank().setInterest(interest);
        for (var client : bankSession.getBank().getSubscribers()) {
            notificationRepository.AddNotification(new InterestNotification(client, bankSession.getBank()));
        }

        return new ServiceResult.Success("Success");
    }

    /**
     * @param commission новая комиссия
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult updateCommission(double commission) {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.BankSession bankSession))
            return new ServiceResult.Failure("You need to be logged in");

        bankSession.getBank().setCommission(commission);
        for (var client : bankSession.getBank().getSubscribers()) {
            notificationRepository.AddNotification(new CommissionNotification(client, bankSession.getBank()));
        }

        return new ServiceResult.Success("Success");
    }


}

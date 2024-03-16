package application.application;

import application.abstractions.*;
import application.contracts.IClientService;
import application.contracts.ICurrentUserManager;
import application.cotracts.LoginResult;
import application.result.ServiceResult;
import domain.interfaces.IAccelerateTimeService;
import domain.interfaces.IAccountFactory;
import domain.interfaces.ITransactionFactory;
import domain.interfaces.Transaction;
import domain.models.Client;
import domain.models.accounts.Account;
import domain.models.notofications.Notification;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Application-service для клиентов
 */
public class ClientService implements IClientService {
    private final ITransactionRepository _transactionRepository;
    private final IAccountRepository _accountRepository;
    private final IClientRepository _clientRepository;
    private final IBankRepository _bankRepository;
    private final INotificationRepository _notificationRepository;
    private final ITransactionFactory _transactionFactory;
    private final IAccountFactory _accountFactory;
    private final IAccelerateTimeService _accelerateService;
    private final ICurrentUserManager _currentUserManager;

    public ClientService(ITransactionRepository transactionRepository, IAccountRepository accountRepository, IClientRepository clientRepository, IBankRepository bankRepository, INotificationRepository notificationRepository, ITransactionFactory transactionFactory, IAccountFactory accountFactory, IAccelerateTimeService accelerateService, ICurrentUserManager currentUserManager) {
        _transactionRepository = transactionRepository;
        _accountRepository = accountRepository;
        _clientRepository = clientRepository;
        _bankRepository = bankRepository;
        _notificationRepository = notificationRepository;
        _transactionFactory = transactionFactory;
        _accountFactory = accountFactory;
        _accelerateService = accelerateService;
        _currentUserManager = currentUserManager;
        _currentUserManager.setCurrentSession(new CurrentSession.UnauthorizedSession());
    }

    /**
     * @param name имя клиента
     * @param surname фамилия клиента
     * @return результат попытки входа
     */
    @Override
    public LoginResult login(String name, String surname) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.UnauthorizedSession))
            return new LoginResult.Failure();

        Client client = _clientRepository.getAllClients()
                .stream()
                .filter(client1 -> client1.get_name().equals(name) && client1.get_surname().equals(surname))
                .findFirst()
                .orElse(null);

        if (client == null) {
            return new LoginResult.NotFound();
        }

        _currentUserManager.setCurrentSession(new CurrentSession.ClientSession(client));
        return new LoginResult.Success();
    }

    /**
     * @return результат выхода из аккаунта
     */
    @Override
    public ServiceResult logout() {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession))
            return new ServiceResult.Failure("You need to be logged in");

        _currentUserManager.setCurrentSession(new CurrentSession.UnauthorizedSession());
        return new ServiceResult.Success("Success");
    }

    /**
     * @param accountId айди аккаунта на который надо положить деньги
     * @param moneyAmount количество денег
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult addMoney(UUID accountId, BigDecimal moneyAmount) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return new ServiceResult.Failure("You need to be logged in");

        Account currentAccount = _accountRepository.findAccount(accountId);
        if (currentAccount == null)
            return new ServiceResult.Failure("Account not found");

        Transaction currentTransaction = _transactionFactory.createAddTransaction(currentAccount, moneyAmount);
        currentTransaction.execute(clientSession.get_client());

        _transactionRepository.addNewTransaction(currentTransaction);
        return new ServiceResult.Success("Success");
    }

    /**
     * @param accountId айди аккаунта
     * @param moneyAmount количество денег
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult withdrawMoney(UUID accountId, BigDecimal moneyAmount) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return new ServiceResult.Failure("You need to be logged in");

        Account currentAccount = _accountRepository.findAccount(accountId);
        if (currentAccount == null)
            return new ServiceResult.Failure("Account not found");

        Transaction currentTransaction = _transactionFactory.createWithdrawTransaction(currentAccount, moneyAmount);
        currentTransaction.execute(clientSession.get_client());

        _transactionRepository.addNewTransaction(currentTransaction);
        return new ServiceResult.Success("Success");
    }

    /**
     * @param fromId айди аккаунта с которого переводятся деньги
     * @param toId айди куда переводятся деньги
     * @param moneyAmount количество денег, которые надо перевести
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult transferMoney(UUID fromId, UUID toId, BigDecimal moneyAmount) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return new ServiceResult.Failure("You need to be logged in");

        Account fromAccount = _accountRepository.findAccount(fromId);
        Account toAccount = _accountRepository.findAccount(toId);

        if (fromAccount == null || toAccount == null)
            return new ServiceResult.Failure("Account not found");

        Transaction transferTransaction = _transactionFactory.createTransferTransaction(fromAccount, toAccount, moneyAmount);
        transferTransaction.execute(clientSession.get_client());

        _transactionRepository.addNewTransaction(transferTransaction);
        return new ServiceResult.Success("Success");
    }

    /**
     * @param bankName имя банка
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult createDebitAccount(String bankName) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return new ServiceResult.Failure("You need to be logged in");

        Account newAccount = _accountFactory.createDebitAccount(clientSession.get_client(), _bankRepository.findBankByName(bankName));
        _accountRepository.addNewAccount(newAccount);
        return new ServiceResult.Success("Success");
    }

    /**
     * @param bankName имя банка, где создается аккаунт
     * @param term время, в течение которого идут выплаты
     * @param moneyAmount количетсво денег, которые кладутся на счет
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult createDepositAccount(String bankName, Integer term, BigDecimal moneyAmount) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return new ServiceResult.Failure("You need to be logged in");

        Account newAccount = _accountFactory.createDepositAccount(clientSession.get_client(), _bankRepository.findBankByName(bankName), term, moneyAmount);
        _accountRepository.addNewAccount(newAccount);
        return new ServiceResult.Success("Success");
    }

    /**
     * @param bankName имя банка, где создается аккаунт
     * @param moneyAmount количество денег, которые кладутся на счет
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult createCreditAccount(String bankName, BigDecimal moneyAmount) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return new ServiceResult.Failure("You need to be logged in");

        Account newAccount = _accountFactory.createCreditAccount(clientSession.get_client(), _bankRepository.findBankByName(bankName), moneyAmount);
        _accountRepository.addNewAccount(newAccount);
        return new ServiceResult.Success("Success");
    }

    /**
     * @param passportData новые паспортные данные
     * @return результат работы сервиса
     */
    public ServiceResult updatePassportData(String passportData) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return new ServiceResult.Failure("You need to be logged in");

        Client client = Client.builder()
                ._id(clientSession.get_client().get_id())
                ._name(clientSession.get_client().get_name())
                ._surname(clientSession.get_client().get_surname())
                ._address(clientSession.get_client().get_address())
                ._passportData(passportData)
                .build();

        _clientRepository.update(clientSession.get_client().get_id(), client);
        return new ServiceResult.Success("Success");
    }

    /**
     * @param address новый адрес аккаунта
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult updateAddress(String address) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return new ServiceResult.Failure("You need to be logged in");

        Client client = Client.builder()
                ._id(clientSession.get_client().get_id())
                ._name(clientSession.get_client().get_name())
                ._surname(clientSession.get_client().get_surname())
                ._address(address)
                ._passportData(clientSession.get_client().get_passportData())
                .build();

        _clientRepository.update(clientSession.get_client().get_id(), client);
        return new ServiceResult.Success("Success");
    }

    /**
     * @return список всех уведомлений
     */
    @Override
    public @Nullable List<Notification> checkClientNotifications() {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return null;

        return _notificationRepository.getAllNotifications()
                .stream()
                .filter(notification -> notification.get_client().equals(clientSession.get_client()))
                .toList();
    }

    /**
     * @return список всех счетов данного клиента
     */
    @Override
    public @Nullable List<Account> checkClientAccounts() {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return null;

        return _accountRepository.getAllAccounts()
                .stream()
                .filter(account -> account.get_client().equals(clientSession.get_client()))
                .toList();
    }

    /**
     * @param date дата к которой надо посмотреть начисленные деньги
     * @param accountId айди аккаунта для которого будет начисление
     * @return результат работы сервиса
     */
    @Override
    public BigDecimal accelerateTime(LocalDate date, UUID accountId) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return null;

        Account account = _accountRepository.findAccount(accountId);
        if (account == null)
            return null;

        return _accelerateService.accelerateTime(date, clientSession.get_client(), account);
    }


}

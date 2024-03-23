package application.application;

import application.abstractions.*;
import application.contracts.IClientService;
import application.contracts.ICurrentUserManager;
import application.result.LoginResult;
import application.result.ServiceResult;
import domain.exceptions.NotFoundException;
import domain.interfaces.IAccelerateTimeService;
import domain.interfaces.IAccountFactory;
import domain.interfaces.ITransactionFactory;
import domain.interfaces.Transaction;
import domain.models.Bank;
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
    private final ITransactionRepository transactionRepository;
    private final IAccountRepository accountRepository;
    private final IClientRepository clientRepository;
    private final IBankRepository bankRepository;
    private final INotificationRepository notificationRepository;
    private final ITransactionFactory transactionFactory;
    private final IAccountFactory accountFactory;
    private final IAccelerateTimeService accelerateService;
    private final ICurrentUserManager currentUserManager;

    public ClientService(ITransactionRepository transactionRepository, IAccountRepository accountRepository, IClientRepository clientRepository, IBankRepository bankRepository, INotificationRepository notificationRepository, ITransactionFactory transactionFactory, IAccountFactory accountFactory, IAccelerateTimeService accelerateService, ICurrentUserManager currentUserManager) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
        this.bankRepository = bankRepository;
        this.notificationRepository = notificationRepository;
        this.transactionFactory = transactionFactory;
        this.accountFactory = accountFactory;
        this.accelerateService = accelerateService;
        this.currentUserManager = currentUserManager;
        this.currentUserManager.setCurrentSession(new CurrentSession.UnauthorizedSession());
    }

    /**
     * @param name имя клиента
     * @param surname фамилия клиента
     * @return результат попытки входа
     */
    @Override
    public LoginResult login(String name, String surname) {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.UnauthorizedSession))
            return LoginResult.FAILURE;

        Client client = clientRepository.getAllClients()
                .stream()
                .filter(client1 -> client1.getName().equals(name) && client1.getSurname().equals(surname))
                .findFirst()
                .orElse(null);

        if (client == null) {
            return LoginResult.NOT_FOUND;
        }

        currentUserManager.setCurrentSession(new CurrentSession.ClientSession(client));
        return LoginResult.SUCCESS;
    }

    /**
     * @return результат выхода из аккаунта
     */
    @Override
    public ServiceResult logout() {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession))
            return new ServiceResult.Failure("You need to be logged in");

        currentUserManager.setCurrentSession(new CurrentSession.UnauthorizedSession());
        return new ServiceResult.Success("Success");
    }

    /**
     * @param accountId айди аккаунта на который надо положить деньги
     * @param moneyAmount количество денег
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult addMoney(UUID accountId, BigDecimal moneyAmount) throws NotFoundException {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return new ServiceResult.Failure("You need to be logged in");

        Account currentAccount = accountRepository.findAccount(accountId);
        if (currentAccount == null)
            throw NotFoundException.accountNotFound();

        Transaction currentTransaction = transactionFactory.createAddTransaction(currentAccount, moneyAmount);
        currentTransaction.execute(clientSession.getClient());

        transactionRepository.addNewTransaction(currentTransaction);
        return new ServiceResult.Success("Success");
    }

    /**
     * @param accountId айди аккаунта
     * @param moneyAmount количество денег
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult withdrawMoney(UUID accountId, BigDecimal moneyAmount) throws NotFoundException {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return new ServiceResult.Failure("You need to be logged in");

        Account currentAccount = accountRepository.findAccount(accountId);
        if (currentAccount == null)
            throw NotFoundException.accountNotFound();

        Transaction currentTransaction = transactionFactory.createWithdrawTransaction(currentAccount, moneyAmount);
        currentTransaction.execute(clientSession.getClient());

        transactionRepository.addNewTransaction(currentTransaction);
        return new ServiceResult.Success("Success");
    }

    /**
     * @param fromId айди аккаунта с которого переводятся деньги
     * @param toId айди куда переводятся деньги
     * @param moneyAmount количество денег, которые надо перевести
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult transferMoney(UUID fromId, UUID toId, BigDecimal moneyAmount) throws NotFoundException {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return new ServiceResult.Failure("You need to be logged in");

        Account fromAccount = accountRepository.findAccount(fromId);
        Account toAccount = accountRepository.findAccount(toId);

        if (fromAccount == null || toAccount == null)
            throw NotFoundException.accountNotFound();

        Transaction transferTransaction = transactionFactory.createTransferTransaction(fromAccount, toAccount, moneyAmount);
        transferTransaction.execute(clientSession.getClient());

        transactionRepository.addNewTransaction(transferTransaction);
        return new ServiceResult.Success("Success");
    }

    /**
     * @param bankName имя банка
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult createDebitAccount(String bankName) {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return new ServiceResult.Failure("You need to be logged in");

        Account newAccount = accountFactory.createDebitAccount(clientSession.getClient(), bankRepository.findBankByName(bankName));
        accountRepository.addNewAccount(newAccount);
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
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return new ServiceResult.Failure("You need to be logged in");

        Account newAccount = accountFactory.createDepositAccount(clientSession.getClient(), bankRepository.findBankByName(bankName), term, moneyAmount);
        accountRepository.addNewAccount(newAccount);
        return new ServiceResult.Success("Success");
    }

    /**
     * @param bankName имя банка, где создается аккаунт
     * @param moneyAmount количество денег, которые кладутся на счет
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult createCreditAccount(String bankName, BigDecimal moneyAmount) {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return new ServiceResult.Failure("You need to be logged in");

        Account newAccount = accountFactory.createCreditAccount(clientSession.getClient(), bankRepository.findBankByName(bankName), moneyAmount);
        accountRepository.addNewAccount(newAccount);
        return new ServiceResult.Success("Success");
    }

    /**
     * @param passportData новые паспортные данные
     * @return результат работы сервиса
     */
    public ServiceResult updatePassportData(String passportData) {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return new ServiceResult.Failure("You need to be logged in");

        Client client = Client.builder()
                .id(clientSession.getClient().getId())
                .name(clientSession.getClient().getName())
                .surname(clientSession.getClient().getSurname())
                .address(clientSession.getClient().getAddress())
                .passportData(passportData)
                .build();

        try {
            clientRepository.update(clientSession.getClient().getId(), client);
        } catch (NotFoundException exception) {
            System.out.println(exception.toString());
        }

        return new ServiceResult.Success("Success");
    }

    /**
     * @param address новый адрес аккаунта
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult updateAddress(String address) {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return new ServiceResult.Failure("You need to be logged in");

        Client client = Client.builder()
                .id(clientSession.getClient().getId())
                .name(clientSession.getClient().getName())
                .surname(clientSession.getClient().getSurname())
                .address(address)
                .passportData(clientSession.getClient().getPassportData())
                .build();

        try {
            clientRepository.update(clientSession.getClient().getId(), client);
        } catch (NotFoundException exception) {
            System.out.println(exception.toString());
        }

        return new ServiceResult.Success("Success");
    }

    /**
     * @return список всех уведомлений
     */
    @Override
    public @Nullable List<Notification> checkClientNotifications() {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return null;

        return notificationRepository.getAllNotifications()
                .stream()
                .filter(notification -> notification.getClient().equals(clientSession.getClient()))
                .toList();
    }

    /**
     * @return список всех счетов данного клиента
     */
    @Override
    public @Nullable List<Account> checkClientAccounts() {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return null;

        return accountRepository.getAllAccounts()
                .stream()
                .filter(account -> account.getClient().equals(clientSession.getClient()))
                .toList();
    }

    /**
     * @param date дата к которой надо посмотреть начисленные деньги
     * @param accountId айди аккаунта для которого будет начисление
     * @return результат работы сервиса
     */
    @Override
    public BigDecimal accelerateTime(LocalDate date, UUID accountId) throws NotFoundException {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return null;

        Account account = accountRepository.findAccount(accountId);
        if (account == null)
            throw NotFoundException.accountNotFound();

        return accelerateService.accelerateTime(date, clientSession.getClient(), account);
    }

    @Override
    public ServiceResult subscribe(String bankName) throws NotFoundException {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession clientSession))
            return new ServiceResult.Failure("You need to be logged in");

        Bank bank = bankRepository.getAllBanks()
                .stream()
                .filter(bank1 -> bank1.getName().equals(bankName))
                .findFirst()
                .orElse(null);

        if (bank == null) {
            throw NotFoundException.bankNotFound();
        }

        bank.subscribe(clientSession.getClient());
        return new ServiceResult.Success("Success");
    }

}

package application.application;

import application.abstractions.*;
import application.contracts.ICentralBankService;
import application.contracts.ICurrentUserManager;
import application.result.LoginResult;
import application.result.ServiceResult;
import domain.exceptions.AlreadyExistsException;
import domain.exceptions.NotFoundException;
import domain.interfaces.*;
import domain.models.Admin;
import domain.models.Bank;
import domain.models.Client;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Application-service для центрального банка
 */
public class CentralBankService implements ICentralBankService {
    private final IAdminRepository adminRepository;
    private final IBankRepository bankRepository;
    private final IClientRepository clientRepository;
    private final ITransactionRepository transactionRepository;
    private final ICreateNewBankService createNewBankService;
    private final ICreateNewClientService createNewClientService;
    private final IMakePaymentService makePaymentService;
    private final IUndoTransactionService undoTransactionService;
    private final ICurrentUserManager currentUserManager;

    public CentralBankService(IAdminRepository adminRepository, IBankRepository bankRepository, IClientRepository clientRepository, ITransactionRepository transactionRepository, ICreateNewBankService createNewBankService, ICreateNewClientService createNewClientService, IMakePaymentService makePaymentService, IUndoTransactionService undoTransactionService, ICurrentUserManager currentUserManager) {
        this.adminRepository = adminRepository;
        this.bankRepository = bankRepository;
        this.clientRepository = clientRepository;
        this.transactionRepository = transactionRepository;
        this.createNewBankService = createNewBankService;
        this.createNewClientService = createNewClientService;
        this.makePaymentService = makePaymentService;
        this.undoTransactionService = undoTransactionService;
        this.currentUserManager = currentUserManager;
        this.currentUserManager.setCurrentSession(new CurrentSession.UnauthorizedSession());
    }

    /**
     * @param password пароль для входа как центральный банк
     * @return результат попытки входа
     */
    @Override
    public LoginResult login(String password) {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.UnauthorizedSession))
            return LoginResult.FAILURE;

        Admin admin = adminRepository.findAdmin(password);

        if (admin == null)
            return LoginResult.FAILURE;

        currentUserManager.setCurrentSession(new CurrentSession.CentralBankSession(admin));
        return LoginResult.SUCCESS;
    }

    /**
     * @return результат выхода из аккаунта
     */
    @Override
    public ServiceResult logout() {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.CentralBankSession))
            return new ServiceResult.Failure("You need to be logged in");

        currentUserManager.setCurrentSession(new CurrentSession.UnauthorizedSession());
        return new ServiceResult.Success("Success");
    }

    /**
     * @param name имя нового банка
     * @param interest проценты по остатку конкретного банка
     * @param commission комиссия у банка
     * @param limit лимит кредитного счета
     * @return результат попытки создания
     */
    @Override
    public ServiceResult createNewBank(String name, double interest, double commission, BigDecimal limit) throws AlreadyExistsException {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.CentralBankSession))
            return new ServiceResult.Failure("You need to be logged in");

        Bank bank = bankRepository.getAllBanks()
                .stream()
                .filter(bank1 -> bank1.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (bank != null)
            throw AlreadyExistsException.bankAlreadyExists();

        bank = createNewBankService.CreateNewBank(name, interest, commission, limit);
        bankRepository.addNewBank(bank);
        return new ServiceResult.Success("Success");
    }

    /**
     * @param name имя нового клиента
     * @param surname фамилия нового клиента
     * @param address адрес нового клиента
     * @param passportData пассспортные данные
     * @return результат попытки создания
     */
    @Override
    public ServiceResult createNewClient(String name, String surname, @Nullable String address, @Nullable String passportData) throws AlreadyExistsException {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.CentralBankSession))
            return new ServiceResult.Failure("You need to be logged in");

        Client client = clientRepository.getAllClients()
                .stream()
                .filter(client1 -> client1.getName().equals(name) && client1.getSurname().equals(surname))
                .findFirst()
                .orElse(null);

        if (client != null)
            throw AlreadyExistsException.clientAlreadyExists();

        client = createNewClientService.createNewClient(name, surname, address, passportData);
        clientRepository.addNewClient(client);
        return new ServiceResult.Success("Success");
    }

    /**
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult makePayment() {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.CentralBankSession))
            return new ServiceResult.Failure("You need to be logged in");

        makePaymentService.MakePayment();
        return new ServiceResult.Success("Success");
    }

    /**
     * @param transactionId айди транзакции которую надо отменить
     * @return результат отмены транзакции
     */
    @Override
    public ServiceResult undoTransaction(UUID transactionId) throws NotFoundException {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.CentralBankSession))
            return new ServiceResult.Failure("You need to be logged in");

        Transaction transaction = transactionRepository.findTransaction(transactionId);
        if (transaction == null)
            throw NotFoundException.transactionNotFound();

        undoTransactionService.undoTransaction(transaction);
        return new ServiceResult.Success("Success");
    }

    /**
     * @return список всех банков системы
     */
    public @Nullable List<Bank> checkAllBanks() {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.CentralBankSession))
            return null;

        return bankRepository.getAllBanks()
                .stream()
                .toList();
    }

    /**
     * @return список всех клиентов системы
     */
    @Override
    public List<Client> checkAllClients() {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.CentralBankSession))
            return null;

        return clientRepository.getAllClients()
                .stream()
                .toList();
    }

    /**
     * @return все транзакции системы
     */
    @Override
    public List<Transaction> checkAllTransactions() {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.CentralBankSession))
            return null;

        return transactionRepository.getAllTransactions()
                .stream()
                .toList();
    }
}

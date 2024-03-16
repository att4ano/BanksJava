package application.application;

import application.abstractions.*;
import application.contracts.ICentralBankService;
import application.contracts.ICurrentUserManager;
import application.cotracts.LoginResult;
import application.result.ServiceResult;
import domain.interfaces.*;
import domain.models.Admin;
import domain.models.Bank;
import domain.models.Client;
import domain.models.notofications.Notification;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Application-service для центрального банка
 */
public class CentralBankService implements ICentralBankService {
    private final IAdminRepository _adminRepository;
    private final IBankRepository _bankRepository;
    private final IClientRepository _clientRepository;
    private final ITransactionRepository _transactionRepository;
    private final ICreateNewBankService _createNewBankService;
    private final ICreateNewClientService _createNewClientService;
    private final IMakePaymentService _makePaymentService;
    private final IUndoTransactionService _undoTransactionService;
    private final ICurrentUserManager _currentUserManager;

    public CentralBankService(IAdminRepository adminRepository, IBankRepository bankRepository, IClientRepository clientRepository, ITransactionRepository transactionRepository, ICreateNewBankService createNewBankService, ICreateNewClientService createNewClientService, IMakePaymentService makePaymentService, IUndoTransactionService undoTransactionService, ICurrentUserManager currentUserManager) {
        _adminRepository = adminRepository;
        _bankRepository = bankRepository;
        _clientRepository = clientRepository;
        _transactionRepository = transactionRepository;
        _createNewBankService = createNewBankService;
        _createNewClientService = createNewClientService;
        _makePaymentService = makePaymentService;
        _undoTransactionService = undoTransactionService;
        _currentUserManager = currentUserManager;
        _currentUserManager.setCurrentSession(new CurrentSession.UnauthorizedSession());
    }

    /**
     * @param password пароль для входа как центральный банк
     * @return результат попытки входа
     */
    @Override
    public LoginResult login(String password) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.UnauthorizedSession))
            return new LoginResult.Failure();

        Admin admin = _adminRepository.findAdmin(password);

        if (admin == null)
            return new LoginResult.Failure();

        _currentUserManager.setCurrentSession(new CurrentSession.CentralBankSession(admin));
        return new LoginResult.Success();
    }

    /**
     * @return результат выхода из аккаунта
     */
    @Override
    public ServiceResult logout() {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.CentralBankSession))
            return new ServiceResult.Failure("You need to be logged in");

        _currentUserManager.setCurrentSession(new CurrentSession.UnauthorizedSession());
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
    public ServiceResult createNewBank(String name, double interest, double commission, BigDecimal limit) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.CentralBankSession))
            return new ServiceResult.Failure("You need to be logged in");

        Bank bank = _bankRepository.getAllBanks()
                .stream()
                .filter(bank1 -> bank1.get_name().equals(name))
                .findFirst()
                .orElse(null);

        if (bank != null)
            return new ServiceResult.Failure("This bank is already exists");

        bank = _createNewBankService.CreateNewBank(name, interest, commission, limit);
        _bankRepository.addNewBank(bank);
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
    public ServiceResult createNewClient(String name, String surname, @Nullable String address, @Nullable String passportData) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.CentralBankSession))
            return new ServiceResult.Failure("You need to be logged in");

        Client client = _clientRepository.getAllClients()
                .stream()
                .filter(client1 -> client1.get_name().equals(name) && client1.get_surname().equals(surname))
                .findFirst()
                .orElse(null);

        if (client != null)
            return new ServiceResult.Failure("This client is already exists");

        client = _createNewClientService.createNewClient(name, surname, address, passportData);
        _clientRepository.addNewClient(client);
        return new ServiceResult.Success("Success");
    }

    /**
     * @return результат работы сервиса
     */
    @Override
    public ServiceResult makePayment() {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.CentralBankSession))
            return new ServiceResult.Failure("You need to be logged in");

        _makePaymentService.MakePayment();
        return new ServiceResult.Success("Success");
    }

    /**
     * @param transactionId айди транзакции которую надо отменить
     * @return результат отмены транзакции
     */
    @Override
    public ServiceResult undoTransaction(UUID transactionId) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.CentralBankSession))
            return new ServiceResult.Failure("You need to be logged in");

        Transaction transaction = _transactionRepository.findTransaction(transactionId);
        if (transaction == null)
            return new ServiceResult.Failure("Transaction not found");

        _undoTransactionService.undoTransaction(transaction);
        _transactionRepository.deleteTransaction(transactionId);
        return new ServiceResult.Success("Success");
    }

    /**
     * @return список всех банков системы
     */
    public @Nullable List<Bank> checkAllBanks() {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.CentralBankSession))
            return null;

        return _bankRepository.getAllBanks()
                .stream()
                .toList();
    }

    /**
     * @return список всех клиентов системы
     */
    @Override
    public List<Client> checkAllClients() {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.CentralBankSession))
            return null;

        return _clientRepository.getAllClients()
                .stream()
                .toList();
    }

    /**
     * @return все транзакции системы
     */
    @Override
    public List<Transaction> checkAllTransactions() {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.CentralBankSession))
            return null;

        return _transactionRepository.getAllTransactions()
                .stream()
                .toList();
    }
}

import Infrastructure.DataAccess.Repositories.*;
import application.abstractions.*;
import application.application.BankService;
import application.application.CentralBankService;
import application.application.ClientService;
import application.application.CurrentUserManager;
import application.contracts.IBankService;
import application.contracts.ICentralBankService;
import application.contracts.IClientService;
import application.contracts.ICurrentUserManager;
import domain.interfaces.*;
import domain.models.Admin;
import domain.models.CentralBank;
import domain.models.Client;
import domain.models.accounts.DebitAccount;
import domain.services.*;
import presentation.console.IScenarioProvider;
import presentation.console.ScenarioRunner;
import presentation.console.scenarios.admin.*;
import presentation.console.scenarios.bank.BankLogoutProvider;
import presentation.console.scenarios.bank.UpdateBankInformationProvider;
import presentation.console.scenarios.client.*;
import presentation.console.scenarios.login.LoginAsAdminProvider;
import presentation.console.scenarios.login.LoginAsBankProvider;
import presentation.console.scenarios.login.LoginAsClientProvider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

/**
 * класс точка входа
 */
public class Main {
    /**
     * определение всех зависимотей и запуск программы
     */
    public static void main(String[] args) {

        var centralBank = new CentralBank();
        ICurrentUserManager currentUserManager = new CurrentUserManager();

        IBankRepository bankRepository = new BankRepository(new HashSet<>());
        IClientRepository clientRepository = new ClientRepository(new HashSet<>());
        INotificationRepository notificationRepository = new NotificationRepository(new HashSet<>());
        IAccountRepository accountRepository = new AccountRepository(new HashSet<>());
        ITransactionRepository transactionRepository = new TransactionRepository(new HashSet<>());
        IAdminRepository adminRepository = new AdminRepository(new HashSet<>());

        var newClient = new Client(UUID.randomUUID(), "alex", "dubstep", "Alpiyka", "12345678");
        var admin = new Admin(UUID.randomUUID(), "1337");
        var newBank = centralBank.createNewBank( "Neiva", 0.1, 0.1, BigDecimal.valueOf(10000));
        var bank = centralBank.createNewBank( "Sber", 0.1, 0.1, BigDecimal.valueOf(100000));
        var newAccount = new DebitAccount(UUID.randomUUID(), newBank, newClient);
        var depositAccount = bank.createDepositAccount(newClient, 3, BigDecimal.valueOf(1000));
        clientRepository.addNewClient(newClient);
        bankRepository.addNewBank(newBank);
        bankRepository.addNewBank(bank);
        adminRepository.AddNewAdmin(admin);
        accountRepository.addNewAccount(newAccount);
        accountRepository.addNewAccount(depositAccount);
        newClient.getAccounts().add(newAccount);

        var client = new Client(UUID.randomUUID(), "nekit", "bitbox", "Vyazma", "123456789");
        var account = new DebitAccount(UUID.randomUUID(), bank, client);
        clientRepository.addNewClient(client);
        accountRepository.addNewAccount(account);

        IAccountFactory accountFactory = new AccountFactory();
        ITransactionFactory transactionFactory = new TransactionFactory();
        IAccelerateTimeService accelerateTimeService = new AccelerateTimeService();
        ICreateNewBankService createNewBankService = new CreateNewBankService(centralBank);
        ICreateNewClientService createNewClientService = new CreateNewClientService(centralBank);
        IUndoTransactionService undoTransactionService = new UndoTransactionService();
        IMakePaymentService makePaymentService = new MakePaymentService(centralBank);

        IClientService clientService = new ClientService(transactionRepository,
                                                        accountRepository,
                                                        clientRepository,
                                                        bankRepository,
                                                        notificationRepository,
                                                        transactionFactory,
                                                        accountFactory,
                                                        accelerateTimeService,
                                                        currentUserManager);

        ICentralBankService centralBankService = new CentralBankService(adminRepository,
                                                                        bankRepository,
                                                                        clientRepository,
                                                                        transactionRepository,
                                                                        createNewBankService,
                                                                        createNewClientService,
                                                                        makePaymentService,
                                                                        undoTransactionService,
                                                                        currentUserManager);

        IBankService bankService = new BankService(bankRepository,
                                                   accountRepository,
                                                   notificationRepository,
                                                   currentUserManager);


        var loginAsAdminProvider= new LoginAsAdminProvider(centralBankService, currentUserManager);
        var loginAsClientProvider = new LoginAsClientProvider(clientService, currentUserManager);
        var loginAsBankProvider = new LoginAsBankProvider(bankService, currentUserManager);

        var createNewClientProvider = new CreateNewClientProvider(centralBankService, currentUserManager);
        var createNewBankProvider = new CreateNewBankProvider(centralBankService, currentUserManager);
        var makePaymentProvider = new MakePaymentProvider(centralBankService, currentUserManager);
        var undoTransactionProvider = new UndoTransactionProvider(centralBankService, currentUserManager);
        var adminLogoutProvider = new AdminLogoutProvider(centralBankService, currentUserManager);
        var checkAllBanksProvider = new CheckAllBanksProvider(centralBankService, currentUserManager);
        var checkAllClientsProvider = new CheckAllClientsProvider(centralBankService, currentUserManager);
        var checkAllTransactionsProvider = new CheckAllTransactionProvider(centralBankService, currentUserManager);

        var addMoneyProvider = new AddMoneyProvider(clientService, currentUserManager);
        var withdrawMoneyProvider = new WithdrawMoneyProvider(clientService, currentUserManager);
        var createNewAccountProvider = new CreateNewAccountProvider(clientService, currentUserManager);
        var updateInformationProvider = new UpdateInformationProvider(clientService, currentUserManager);
        var transferMoneyProvider = new TransferMoneyProvider(clientService, currentUserManager);
        var checkNotificationsProvider = new CheckNotificationsProvider(clientService, currentUserManager);
        var checkAccountsProvider = new CheckAccountsProvider(clientService, currentUserManager);
        var accelerateTimeProvider = new AccelerateTimeProvider(clientService, currentUserManager);
        var clientLogoutProvider = new ClientLogoutProvider(clientService, currentUserManager);


        var updateBankInformationProvider = new UpdateBankInformationProvider(bankService, currentUserManager);
        var bankLogoutScenario = new BankLogoutProvider(bankService, currentUserManager);

        List<IScenarioProvider> providers = new ArrayList<>();
        providers.add(loginAsAdminProvider);
        providers.add(loginAsClientProvider);
        providers.add(loginAsBankProvider);
        providers.add(createNewClientProvider);
        providers.add(createNewBankProvider);
        providers.add(makePaymentProvider);
        providers.add(undoTransactionProvider);
        providers.add(addMoneyProvider);
        providers.add(withdrawMoneyProvider);
        providers.add(createNewAccountProvider);
        providers.add(updateInformationProvider);
        providers.add(transferMoneyProvider);
        providers.add(checkNotificationsProvider);
        providers.add(checkAccountsProvider);
        providers.add(accelerateTimeProvider);
        providers.add(updateBankInformationProvider);
        providers.add(checkAllClientsProvider);
        providers.add(checkAllBanksProvider);
        providers.add(checkAllTransactionsProvider);
        providers.add(adminLogoutProvider);
        providers.add(clientLogoutProvider);
        providers.add(bankLogoutScenario);

        ScenarioRunner scenarioRunner = new ScenarioRunner(providers);

        while (true) {
            scenarioRunner.run();
        }

    }
}

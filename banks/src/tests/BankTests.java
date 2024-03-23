package tests;

import Infrastructure.DataAccess.Repositories.*;
import application.abstractions.*;
import application.application.*;
import application.contracts.IBankService;
import application.contracts.ICentralBankService;
import application.contracts.IClientService;
import application.contracts.ICurrentUserManager;
import domain.exceptions.NotFoundException;
import domain.interfaces.*;
import domain.models.Admin;
import domain.models.CentralBank;
import domain.models.Client;
import domain.models.accounts.Account;
import domain.models.accounts.CreditAccount;
import domain.models.accounts.DebitAccount;
import domain.models.accounts.DepositAccount;
import domain.services.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;

public class BankTests {
    private final IBankRepository bankRepository = new BankRepository(new HashSet<>());
    private final IClientRepository clientRepository = new ClientRepository(new HashSet<>());
    private final INotificationRepository notificationRepository = new NotificationRepository(new HashSet<>());
    private final IAccountRepository accountRepository = new AccountRepository(new HashSet<>());
    private final ITransactionRepository transactionRepository = new TransactionRepository(new HashSet<>());
    private final IAdminRepository adminRepository = new AdminRepository(new HashSet<>());
    private final CentralBank centralBank = new CentralBank();
    private final ICurrentUserManager currentUserManager = new CurrentUserManager();
    private final IAccountFactory accountFactory = new AccountFactory();
    private final ITransactionFactory transactionFactory = new TransactionFactory();
    private final IAccelerateTimeService accelerateTimeService = new AccelerateTimeService();
    private final ICreateNewBankService createNewBankService = new CreateNewBankService(centralBank);
    private final ICreateNewClientService createNewClientService = new CreateNewClientService(centralBank);
    private final IUndoTransactionService undoTransactionService = new UndoTransactionService();
    private final IMakePaymentService makePaymentService = new MakePaymentService(centralBank);
    private final IClientService clientService = new ClientService(transactionRepository,
            accountRepository,
            clientRepository,
            bankRepository,
            notificationRepository,
            transactionFactory,
            accountFactory,
            accelerateTimeService,
            currentUserManager);

    private final ICentralBankService centralBankService = new CentralBankService(adminRepository,
            bankRepository,
            clientRepository,
            transactionRepository,
            createNewBankService,
            createNewClientService,
            makePaymentService,
            undoTransactionService,
            currentUserManager);

    private final IBankService bankService = new BankService(bankRepository,
            accountRepository,
            notificationRepository,
            currentUserManager);

    @Test
    public void createClientTest() {
        var newClient = centralBank.createNewClient( "alex", "dubstep", "Alpiyka", "12345678");
        Assert.assertEquals(newClient.getName(), "alex");
        Assert.assertEquals(newClient.getSurname(), "dubstep");
        Assert.assertEquals(newClient.getAddress(), "Alpiyka");
        Assert.assertEquals(newClient.getPassportData(), "12345678");
    }

    @Test
    public void createBankTest() {
        var newBank = centralBank.createNewBank( "Neiva", 0.1, 0.1, BigDecimal.valueOf(10000));
        Assert.assertEquals(newBank.getName(), "Neiva");
        Assert.assertEquals(0.1, newBank.getInterest(), 0.0);
        Assert.assertEquals(0.1, newBank.getCommission(), 0.0);
        Assert.assertEquals(newBank.getLimit(), BigDecimal.valueOf(10000));
    }

    @Test
    public void createNewDebitAccount() {
        var client = centralBank.createNewClient( "alex", "dubstep", "Alpiyka", "12345678");
        var bank = centralBank.createNewBank( "Neiva", 0.1, 0.1, BigDecimal.valueOf(10000));
        Account account = bank.createDebitAccount(client);
        Assert.assertEquals(account.getBank(), bank);
        Assert.assertEquals(account.getClient(), client);
        Assert.assertEquals(account.getMoneyAmount(), BigDecimal.valueOf(0));
    }

    @Test
    public void createNewDepositAccount() {
        var client = centralBank.createNewClient( "alex", "dubstep", "Alpiyka", "12345678");
        var bank = centralBank.createNewBank( "Neiva", 0.1, 0.1, BigDecimal.valueOf(10000));
        DepositAccount account = (DepositAccount) bank.createDepositAccount(client, 3, BigDecimal.valueOf(10000));
        Assert.assertEquals(account.getBank(), bank);
        Assert.assertEquals(account.getClient(), client);
        Assert.assertEquals(account.getMoneyAmount(), BigDecimal.valueOf(10000));
        Assert.assertTrue(account.getTerm() == 3);
    }

    @Test
    public void createNewCreditAccount() {
        var client = centralBank.createNewClient( "alex", "dubstep", "Alpiyka", "12345678");
        var bank = centralBank.createNewBank( "Neiva", 0.1, 0.1, BigDecimal.valueOf(10000));
        CreditAccount account = (CreditAccount) bank.createCreditAccount(client, BigDecimal.valueOf(10000));
        Assert.assertEquals(account.getBank(), bank);
        Assert.assertEquals(account.getClient(), client);
        Assert.assertEquals(account.getMoneyAmount(), BigDecimal.valueOf(10000));
    }

    @Test
    public void LoginLogoutTest() {
        var client = centralBank.createNewClient( "alex", "dubstep", "Alpiyka", "12345678");
        clientRepository.addNewClient(client);
        var admin = new Admin(UUID.randomUUID(), "1337");
        adminRepository.AddNewAdmin(admin);
        var bank = centralBank.createNewBank( "Neiva", 0.1, 0.1, BigDecimal.valueOf(10000));
        bankRepository.addNewBank(bank);
        Assert.assertTrue(currentUserManager.getCurrentSession() instanceof CurrentSession.UnauthorizedSession);
        clientService.login("alex", "dubstep");
        Assert.assertTrue(currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession);
        clientService.logout();
        centralBankService.login("1337");
        Assert.assertTrue(currentUserManager.getCurrentSession() instanceof CurrentSession.CentralBankSession);
        centralBankService.logout();
        bankService.login("Neiva");
        Assert.assertTrue(currentUserManager.getCurrentSession() instanceof CurrentSession.BankSession);
        bankService.logout();
        Assert.assertTrue(currentUserManager.getCurrentSession() instanceof CurrentSession.UnauthorizedSession);
    }

    @Test
    public void AddMoneyToAccountTest() {
        var client = centralBank.createNewClient( "alex", "dubstep", "Alpiyka", "12345678");
        clientRepository.addNewClient(client);
        var admin = new Admin(UUID.randomUUID(), "1337");
        adminRepository.AddNewAdmin(admin);
        var bank = centralBank.createNewBank( "Neiva", 0.1, 0.1, BigDecimal.valueOf(10000));
        bankRepository.addNewBank(bank);
        Account account = bank.createDebitAccount(client);
        accountRepository.addNewAccount(account);
        clientService.login("alex", "dubstep");
        try {
            clientService.addMoney(account.getId(), BigDecimal.valueOf(10000));
        } catch (NotFoundException exception) {
            System.out.println(exception.toString());
        }
        Assert.assertEquals(account.getMoneyAmount(), BigDecimal.valueOf(10000));
    }

    @Test
    public void WithdrawMoneyToAccountTest() {
        var client = centralBank.createNewClient( "alex", "dubstep", "Alpiyka", "12345678");
        clientRepository.addNewClient(client);
        var admin = new Admin(UUID.randomUUID(), "1337");
        adminRepository.AddNewAdmin(admin);
        var bank = centralBank.createNewBank( "Neiva", 0.1, 0.1, BigDecimal.valueOf(10000));
        bankRepository.addNewBank(bank);
        Account account = bank.createDebitAccount(client);
        accountRepository.addNewAccount(account);
        clientService.login("alex", "dubstep");
        try {
            clientService.addMoney(account.getId(), BigDecimal.valueOf(10000));
        } catch (NotFoundException exception) {
            System.out.println(exception.toString());
        }
        Assert.assertEquals(account.getMoneyAmount(), BigDecimal.valueOf(10000));
    }

    @Test
    public void TransferMoneyInOneBankTest() {
        var client = centralBank.createNewClient( "alex", "dubstep", "Alpiyka", "12345678");
        clientRepository.addNewClient(client);
        var admin = new Admin(UUID.randomUUID(), "1337");
        adminRepository.AddNewAdmin(admin);
        var bank = centralBank.createNewBank( "Neiva", 0.1, 0.1, BigDecimal.valueOf(10000));
        bankRepository.addNewBank(bank);
        Account account = bank.createDebitAccount(client);
        accountRepository.addNewAccount(account);

        var newClient = new Client(UUID.randomUUID(), "nekit", "bitbox", "Vyazma", "123456789");
        var newAccount = new DebitAccount(UUID.randomUUID(), bank, client);
        clientRepository.addNewClient(newClient);
        accountRepository.addNewAccount(newAccount);

        clientService.login("alex", "dubstep");
        try {
            clientService.addMoney(account.getId(), BigDecimal.valueOf(10000));
            clientService.transferMoney(account.getId(), newAccount.getId(), BigDecimal.valueOf(500));
        } catch (NotFoundException exception) {
            System.out.println(exception.toString());
        }
        Assert.assertEquals(newAccount.getMoneyAmount(), BigDecimal.valueOf(500));
    }

    @Test
    public void TransferMoneyInDifferentBankTest() {
        var client = centralBank.createNewClient( "alex", "dubstep", "Alpiyka", "12345678");
        clientRepository.addNewClient(client);
        var admin = new Admin(UUID.randomUUID(), "1337");
        adminRepository.AddNewAdmin(admin);
        var bank = centralBank.createNewBank( "Neiva", 0.1, 0.1, BigDecimal.valueOf(10000));
        bankRepository.addNewBank(bank);
        Account account = bank.createDebitAccount(client);
        accountRepository.addNewAccount(account);

        var newClient = new Client(UUID.randomUUID(), "nekit", "bitbox", "Vyazma", "123456789");
        clientRepository.addNewClient(newClient);

        var newBank = centralBank.createNewBank( "Sber", 0.1, 0.1, BigDecimal.valueOf(10000));
        bankRepository.addNewBank(newBank);
        var newAccount = new DebitAccount(UUID.randomUUID(), bank, client);
        accountRepository.addNewAccount(newAccount);

        clientService.login("alex", "dubstep");
        try {
            clientService.addMoney(account.getId(), BigDecimal.valueOf(10000));
            clientService.transferMoney(account.getId(), newAccount.getId(), BigDecimal.valueOf(500));
        } catch (NotFoundException exception) {
            System.out.println(exception.toString());
        }
        Assert.assertEquals(newAccount.getMoneyAmount(), BigDecimal.valueOf(500));
    }

    @Test
    public void DepositPaymentTest() {
        var client = centralBank.createNewClient( "alex", "dubstep", "Alpiyka", "12345678");
        clientRepository.addNewClient(client);
        var admin = new Admin(UUID.randomUUID(), "1337");
        adminRepository.AddNewAdmin(admin);
        var bank = centralBank.createNewBank( "Neiva", 0.1, 0.1, BigDecimal.valueOf(10000));
        bankRepository.addNewBank(bank);
        Account account = bank.createDepositAccount(client, 3, BigDecimal.valueOf(10000));
        accountRepository.addNewAccount(account);

        centralBankService.login("1337");
        centralBankService.makePayment();
        Assert.assertEquals(account.getMoneyAmount(), BigDecimal.valueOf(11000.0));
    }

    @Test
    public void NotificationTest() {
        var client = centralBank.createNewClient( "alex", "dubstep", "Alpiyka", "12345678");
        clientRepository.addNewClient(client);
        var admin = new Admin(UUID.randomUUID(), "1337");
        adminRepository.AddNewAdmin(admin);
        var bank = centralBank.createNewBank( "Neiva", 0.1, 0.1, BigDecimal.valueOf(10000));
        bank.subscribe(client);
        bankRepository.addNewBank(bank);
        Account account = bank.createDepositAccount(client, 3, BigDecimal.valueOf(10000));
        accountRepository.addNewAccount(account);

        bankService.login("Neiva");
        bankService.updateInterest(0.2);
        Assert.assertEquals(notificationRepository.getAllNotifications().size(), 1);
    }
}

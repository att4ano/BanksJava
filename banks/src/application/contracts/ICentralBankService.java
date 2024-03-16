package application.contracts;

import application.cotracts.LoginResult;
import application.result.ServiceResult;
import domain.interfaces.Transaction;
import domain.models.Bank;
import domain.models.Client;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ICentralBankService {
    LoginResult login(String password);
    ServiceResult logout();
    ServiceResult createNewBank(String name, double interest, double commission, BigDecimal limit);
    ServiceResult createNewClient(String name, String surname, @Nullable String address, @Nullable String passportData);
    ServiceResult makePayment();
    ServiceResult undoTransaction(UUID transactionId);
    List<Bank> checkAllBanks();
    List<Client> checkAllClients();
    List<Transaction> checkAllTransactions();
}

package application.contracts;

import application.result.LoginResult;
import application.result.ServiceResult;
import domain.exceptions.AlreadyExistsException;
import domain.exceptions.NotFoundException;
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
    ServiceResult createNewBank(String name, double interest, double commission, BigDecimal limit) throws AlreadyExistsException;
    ServiceResult createNewClient(String name, String surname, @Nullable String address, @Nullable String passportData) throws AlreadyExistsException;
    ServiceResult makePayment();
    ServiceResult undoTransaction(UUID transactionId) throws NotFoundException;
    List<Bank> checkAllBanks();
    List<Client> checkAllClients();
    List<Transaction> checkAllTransactions();
}

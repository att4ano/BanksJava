package application.abstractions;

import domain.interfaces.Transaction;

import java.util.HashSet;
import java.util.UUID;

public interface ITransactionRepository
{
    HashSet<Transaction> getAllTransactions();
    Transaction findTransaction(UUID operationId);
    void addNewTransaction(Transaction operation);
    void deleteTransaction(UUID operation);
}

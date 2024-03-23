package application.abstractions;

import domain.interfaces.Transaction;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public interface ITransactionRepository
{
    Set<Transaction> getAllTransactions();
    Transaction findTransaction(UUID operationId);
    void addNewTransaction(Transaction operation);
}

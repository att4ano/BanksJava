package Infrastructure.DataAccess.Repositories;

import application.abstractions.ITransactionRepository;
import domain.interfaces.Transaction;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * репозиторий операций
 */
public class TransactionRepository implements ITransactionRepository
{
    private final Set<Transaction> transactions;

    public TransactionRepository(HashSet<Transaction> transactions)
    {
        this.transactions = transactions;
    }

    /**
     * @return получить все операции
     */
    @Override
    public Set<Transaction> getAllTransactions()
    {
        return transactions;
    }

    /**
     * @param operationId айди операции
     * @return конкретная операция
     */
    @Override
    public Transaction findTransaction(UUID operationId)
    {
        return transactions.stream()
                .filter(transaction -> transaction.getId().equals(operationId))
                .findFirst()
                .orElse(null);
    }

    /**
     * @param operation добавить операцию
     */
    @Override
    public void addNewTransaction(Transaction operation)
    {
        transactions.add(operation);
    }
}

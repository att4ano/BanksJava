package Infrastructure.DataAccess.Repositories;

import application.abstractions.ITransactionRepository;
import domain.interfaces.Transaction;

import java.util.HashSet;
import java.util.UUID;

/**
 * репозиторий операций
 */
public class TransactionRepository implements ITransactionRepository
{
    private final HashSet<Transaction> _transactions;

    public TransactionRepository(HashSet<Transaction> transactions)
    {
        _transactions = transactions;
    }

    /**
     * @return получить все операции
     */
    @Override
    public HashSet<Transaction> getAllTransactions()
    {
        return _transactions;
    }

    /**
     * @param operationId айди операции
     * @return конкретная операция
     */
    @Override
    public Transaction findTransaction(UUID operationId)
    {
        return _transactions.stream()
                .filter(transaction -> transaction.get_id().equals(operationId))
                .findFirst()
                .orElse(null);
    }

    /**
     * @param operation добавить операцию
     */
    @Override
    public void addNewTransaction(Transaction operation)
    {
        _transactions.add(operation);
    }

    /**
     * @param operationId удалить операцию
     */
    @Override
    public void deleteTransaction(UUID operationId)
    {
        Transaction deleteOperation = _transactions.stream()
                .filter(operation -> operation.get_id().equals(operationId))
                .findFirst()
                .orElse(null);

        if (deleteOperation == null)
            return;

        _transactions.remove(deleteOperation);
    }
}

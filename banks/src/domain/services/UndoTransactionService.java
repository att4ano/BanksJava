package domain.services;

import domain.interfaces.IUndoTransactionService;
import domain.interfaces.Transaction;

/**
 * сервис отмены транзакции
 */
public class UndoTransactionService implements IUndoTransactionService {
    public UndoTransactionService() {

    }

    /**
     * @param transaction транзакция для отмены
     */
    @Override
    public void undoTransaction(Transaction transaction) {
        transaction.undo();
    }
}

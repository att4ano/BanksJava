package domain.interfaces;

import domain.models.Client;
import domain.models.transactions.TransactionStatus;
import lombok.Data;

import java.util.UUID;

/**
 * Абстрактный класс транзакции, описывающий что умеет транзакция
 */
@Data
public abstract class Transaction {
    protected final UUID id;
    protected TransactionStatus status;

    protected Transaction(UUID id) {
        this.id = id;
    }

    public abstract void execute(Client client);
    public abstract void undo();
    public abstract String toString();
}

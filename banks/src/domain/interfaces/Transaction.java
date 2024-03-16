package domain.interfaces;

import domain.models.Client;
import lombok.Data;

import java.util.UUID;

/**
 * Абстрактный класс транзакции, описывающий что умеет транзакция
 */
@Data
public abstract class Transaction {
    protected final UUID _id;

    protected Transaction(UUID id) {
        _id = id;
    }

    public abstract void execute(Client client);
    public abstract void undo();
    public abstract String toString();
}

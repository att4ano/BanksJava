package domain.models.transactions;

import domain.exceptions.DomainException;
import domain.interfaces.Transaction;
import domain.models.Client;
import domain.models.accounts.Account;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public class TransferMoneyTransaction extends Transaction {
    private final Account fromAccount;
    private final Account toAccount;
    private final BigDecimal moneyAmount;

    public TransferMoneyTransaction(Account fromAccount, Account toAccount, BigDecimal moneyAmount) {
        super(UUID.randomUUID());
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.moneyAmount = moneyAmount;
    }

    @Override
    public void execute(@NotNull Client client) {
        client.transferMoney(fromAccount, toAccount, moneyAmount);
        status = TransactionStatus.DONE;
    }

    @Override
    public void undo() {
        try {
            fromAccount.addMoney(moneyAmount);
            toAccount.withdrawMoney(moneyAmount);
            status = TransactionStatus.CANCELLED;
        } catch (DomainException exception) {
            System.out.println(exception.toString());
        }
    }

    @Override
    public String toString() {
        return "Id: " + id.toString() + " | " + "Type: Transfer" + " | " + "Account: " + fromAccount.getId() + " | " + "Money amount: " + moneyAmount.toString();
    }
}

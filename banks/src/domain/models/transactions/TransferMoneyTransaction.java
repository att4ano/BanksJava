package domain.models.transactions;

import domain.interfaces.Transaction;
import domain.models.Client;
import domain.models.accounts.Account;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public class TransferMoneyTransaction extends Transaction {
    private final Account _fromAccount;
    private final Account _toAccount;
    private final BigDecimal _moneyAmount;

    public TransferMoneyTransaction(Account fromAccount, Account toAccount, BigDecimal moneyAmount) {
        super(UUID.randomUUID());
        _fromAccount = fromAccount;
        _toAccount = toAccount;
        _moneyAmount = moneyAmount;
    }

    @Override
    public void execute(@NotNull Client client) {
        client.transferMoney(_fromAccount, _toAccount, _moneyAmount);
    }

    @Override
    public void undo() {
        _fromAccount.addMoney(_moneyAmount);
        _toAccount.withdrawMoney(_moneyAmount);
    }

    @Override
    public String toString() {
        return "Id: " + _id.toString() + " | " + "Type: Transfer" + " | " + "Account: " + _fromAccount.get_id() + " | " + "Money amount: " + _moneyAmount.toString();
    }
}

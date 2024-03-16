package domain.models.transactions;

import domain.interfaces.Transaction;
import domain.models.Client;
import domain.models.accounts.Account;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public class AddMoneyTransaction extends Transaction {
    private final Account _account;
    private final BigDecimal _moneyAmount;

    public AddMoneyTransaction(Account account, BigDecimal moneyAmount) {
        super(UUID.randomUUID());
        _account = account;
        _moneyAmount = moneyAmount;
    }

    @Override
    public void execute(@NotNull Client client) {
        client.addMoney(_account, _moneyAmount);
    }

    @Override
    public void undo() {
        _account.withdrawMoney(_moneyAmount);
    }

    @Override
    public String toString() {
        return "Id: " + _id.toString() + " | " + "Type: Addition" + " | " + "Account: " + _account.get_id() + " | " + "Money amount: " + _moneyAmount.toString();
    }
}

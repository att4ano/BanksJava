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
public class WithdrawMoneyTransaction extends Transaction {
    private final Account account;
    private final BigDecimal moneyAmount;
    public WithdrawMoneyTransaction(Account account, BigDecimal moneyAmount) {
        super(UUID.randomUUID());
        this.account = account;
        this.moneyAmount = moneyAmount;
    }

    @Override
    public void execute(@NotNull Client client) {
        client.withdrawMoney(account, moneyAmount);
        status = TransactionStatus.DONE;
    }

    @Override
    public void undo() {
        try{
            account.addMoney(moneyAmount);
            status = TransactionStatus.CANCELLED;
        } catch (DomainException exception) {
            System.out.println(exception.toString());
        }
    }

    @Override
    public String toString() {
        return "Id: " + id.toString() + " | " + "Type: Withdraw" + " | " + "Account: " + account.getId() + " | " + "Money amount: " + moneyAmount.toString();
    }
}

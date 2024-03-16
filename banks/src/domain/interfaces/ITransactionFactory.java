package domain.interfaces;

import domain.models.accounts.Account;

import java.math.BigDecimal;

public interface ITransactionFactory {
    Transaction createAddTransaction(Account account, BigDecimal moneyAmount);
    Transaction createWithdrawTransaction(Account account, BigDecimal moneyAmount);
    Transaction createTransferTransaction(Account fromAccount, Account toAccount, BigDecimal moneyAmount);
}

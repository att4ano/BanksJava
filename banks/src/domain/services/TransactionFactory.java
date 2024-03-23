package domain.services;

import domain.interfaces.ITransactionFactory;
import domain.interfaces.Transaction;
import domain.models.accounts.Account;
import domain.models.transactions.AddMoneyTransaction;
import domain.models.transactions.TransferMoneyTransaction;
import domain.models.transactions.WithdrawMoneyTransaction;

import java.math.BigDecimal;

/**
 * класс для создания транзакций
 */
public class TransactionFactory implements ITransactionFactory {
    /**
     * @param account аккаунт куда добавляются деньги
     * @param moneyAmount количество денег
     * @return созданная транзакция
     */
    @Override
    public Transaction createAddTransaction(Account account, BigDecimal moneyAmount) {
        return AddMoneyTransaction.builder()
                .account(account)
                .moneyAmount(moneyAmount)
                .build();
    }

    /**
     * @param account аккаунт откуда снимаются деньги
     * @param moneyAmount количество денег
     * @return созданная транзакция
     */
    @Override
    public Transaction createWithdrawTransaction(Account account, BigDecimal moneyAmount) {
        return WithdrawMoneyTransaction.builder()
                .account(account)
                .moneyAmount(moneyAmount)
                .build();
    }

    /**
     * @param fromAccount откуда перевод
     * @param toAccount куда перевод
     * @param moneyAmount сколько денег переводится
     * @return созданная транзакция
     */
    @Override
    public Transaction createTransferTransaction(Account fromAccount, Account toAccount, BigDecimal moneyAmount) {
        return TransferMoneyTransaction.builder()
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .moneyAmount(moneyAmount)
                .build();
    }
}

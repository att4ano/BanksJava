package domain.interfaces;

import domain.models.Bank;
import domain.models.Client;
import domain.models.accounts.Account;

import java.math.BigDecimal;

public interface IAccountFactory {
    Account createDebitAccount(Client client, Bank bank);
    Account createCreditAccount(Client client, Bank bank, BigDecimal moneyAmount);
    Account createDepositAccount(Client client, Bank bank, Integer term, BigDecimal moneyAmount);
}

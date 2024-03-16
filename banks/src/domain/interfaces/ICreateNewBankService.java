package domain.interfaces;

import domain.models.Bank;

import java.math.BigDecimal;

public interface ICreateNewBankService {
    Bank CreateNewBank(String name, double interest, double commission, BigDecimal limit);
}

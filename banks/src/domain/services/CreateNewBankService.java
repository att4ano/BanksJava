package domain.services;

import domain.interfaces.ICreateNewBankService;
import domain.models.Bank;
import domain.models.CentralBank;

import java.math.BigDecimal;

/**
 * сервис создания банка
 */
public class CreateNewBankService implements ICreateNewBankService {
    private final CentralBank _centralBank;
    public CreateNewBankService(CentralBank centralBank) {
        _centralBank = centralBank;
    }

    /**
     * @param name название банка
     * @param interest проценты длядепозитный счетов
     * @param commission комиссия
     * @param limit лимит
     * @return созданный банк
     */
    @Override
    public Bank CreateNewBank(String name, double interest, double commission, BigDecimal limit) {
        return _centralBank.createNewBank(name, interest, commission, limit);
    }
}

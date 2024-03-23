package domain.services;

import domain.interfaces.IMakePaymentService;
import domain.models.CentralBank;

/**
 * сервис для выплат по счетам
 */
public class MakePaymentService implements IMakePaymentService {
    private final CentralBank _centralBank;

    public MakePaymentService(CentralBank centralBank) {
        _centralBank = centralBank;
    }

    /**
     * выплата по счетам
     */
    @Override
    public void MakePayment() {
        _centralBank.makePayment();
    }
}

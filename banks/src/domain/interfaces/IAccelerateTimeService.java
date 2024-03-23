package domain.interfaces;

import domain.models.Client;
import domain.models.accounts.Account;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IAccelerateTimeService {
    BigDecimal accelerateTime(LocalDate newDate, Client client, Account account);
}

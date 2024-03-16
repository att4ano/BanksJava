package domain.services;

import domain.interfaces.IAccelerateTimeService;
import domain.models.Client;
import domain.models.accounts.Account;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * серыис ускорения времени
 */
public class AccelerateTimeService implements IAccelerateTimeService {

    public AccelerateTimeService() { }


    /**
     * @param newDate дата до которой ускоряют время
     * @param client клиент от личца которого это делабт
     * @param account счет для которого это делают
     * @return деньги для ускорения
     */
    @Override
    public BigDecimal accelerateTime(LocalDate newDate, Client client, @NotNull Account account) {
        if (!account.get_client().equals(client))
            return null;

        double dayInterest = account.get_bank().get_interest() / 365;
        BigDecimal result = account.get_moneyAmount();

        int diffDays = LocalDate.now().until(newDate).getDays();

        for (int i = 0; i < diffDays; ++i) {
            result = result.add(result.multiply(BigDecimal.valueOf(dayInterest)));
        }

        return result;
    }
}

package Infrastructure.DataAccess.Repositories;

import application.abstractions.IBankRepository;
import domain.models.Bank;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.UUID;

/**
 * репозиторий банка
 */
public class BankRepository implements IBankRepository
{
    private final HashSet<Bank> _banks;

    public BankRepository(HashSet<Bank> banks)
    {
        _banks = banks;
    }

    /**
     * @return все банки
     */
    @Override
    public HashSet<Bank> getAllBanks()
    {
        return _banks;
    }

    /**
     * @param id айди банка
     * @return конкретный банк
     */
    @Override
    public @Nullable Bank findBank(UUID id) {
        return _banks.stream()
                .filter(bank -> bank.get_id().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public @Nullable Bank findBankByName(String bankName) {
        return _banks.stream()
                .filter(bank -> bank.get_name().equals(bankName))
                .findFirst()
                .orElse(null);
    }

    /**
     * @param bank банк который надо доавить
     */
    @Override
    public void addNewBank(Bank bank)
    {
        _banks.add(bank);
    }

    @Override
    public void update(Bank bank) {
        Bank currentBank = _banks.stream()
                .filter(bank1 -> bank1.get_id().equals(bank.get_id()))
                .findFirst()
                .orElse(null);

        _banks.remove(currentBank);
        _banks.add(bank);
    }


}

package Infrastructure.DataAccess.Repositories;

import application.abstractions.IBankRepository;
import domain.exceptions.NotFoundException;
import domain.models.Bank;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * репозиторий банка
 */
public class BankRepository implements IBankRepository
{
    private final Set<Bank> banks;

    public BankRepository(HashSet<Bank> banks)
    {
        this.banks = banks;
    }

    /**
     * @return все банки
     */
    @Override
    public Set<Bank> getAllBanks()
    {
        return banks;
    }

    /**
     * @param id айди банка
     * @return конкретный банк
     */
    @Override
    public @Nullable Bank findBank(UUID id) {
        return banks.stream()
                .filter(bank -> bank.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public @Nullable Bank findBankByName(String bankName) {
        return banks.stream()
                .filter(bank -> bank.getName().equals(bankName))
                .findFirst()
                .orElse(null);
    }

    /**
     * @param bank банк который надо доавить
     */
    @Override
    public void addNewBank(Bank bank)
    {
        banks.add(bank);
    }

    @Override
    public void update(UUID bankId, Bank bank) throws NotFoundException {
        Bank currentBank = banks.stream()
                .filter(bank1 -> bank1.getId().equals(bankId))
                .findFirst()
                .orElse(null);

        if (currentBank == null) {
            throw NotFoundException.bankNotFound();
        }

        currentBank.setName(bank.getName());
        currentBank.setInterest(bank.getInterest());
        currentBank.setCommission(bank.getCommission());
        currentBank.setLimit(bank.getLimit());
        currentBank.setAccounts(bank.getAccounts());
        currentBank.setSubscribers(bank.getSubscribers());
    }


}

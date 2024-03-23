package application.abstractions;

import domain.exceptions.NotFoundException;
import domain.models.Bank;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public interface IBankRepository
{
    Set<Bank> getAllBanks();

    @Nullable Bank findBank(UUID id);
    @Nullable Bank findBankByName(String bankName);

    void addNewBank(Bank bank);

    void update(UUID bankId, Bank bank) throws NotFoundException;
}

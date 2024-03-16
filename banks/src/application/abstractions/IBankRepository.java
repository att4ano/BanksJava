package application.abstractions;

import domain.models.Bank;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.UUID;

public interface IBankRepository
{
    HashSet<Bank> getAllBanks();

    @Nullable Bank findBank(UUID id);
    @Nullable Bank findBankByName(String bankName);

    void addNewBank(Bank bank);

    void update(Bank bank);
}

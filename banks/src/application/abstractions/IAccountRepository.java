package application.abstractions;

import domain.models.accounts.Account;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public interface IAccountRepository
{
    Set<Account> getAllAccounts();

    @Nullable Account findAccount(UUID Id);

    void addNewAccount(Account account);
}

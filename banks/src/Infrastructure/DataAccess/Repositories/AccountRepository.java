package Infrastructure.DataAccess.Repositories;

import application.abstractions.IAccountRepository;
import domain.models.accounts.Account;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * класс репозитория
 */
public class AccountRepository implements IAccountRepository
{
    private final Set<Account> accounts;

    public AccountRepository(HashSet<Account> accounts)
    {
        this.accounts = accounts;
    }

    /**
     * @return возвращает все счета
     */
    @Override
    public Set<Account> getAllAccounts()
    {
        return accounts;
    }

    /**
     * @param id айди счета
     * @return счет
     */
    @Override
    public @Nullable Account findAccount(UUID id) {
        return accounts.stream()
                .filter(account -> account.getId().equals(id))
                .findFirst()
                .orElse(null);
    }


    /**
     * @param account счет, который надо добавить
     */
    @Override
    public void addNewAccount(Account account)
    {
        accounts.add(account);
    }
}

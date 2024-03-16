package Infrastructure.DataAccess.Repositories;

import application.abstractions.IAccountRepository;
import domain.models.accounts.Account;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.UUID;

/**
 * класс репозитория
 */
public class AccountRepository implements IAccountRepository
{
    private final HashSet<Account> _accounts;

    public AccountRepository(HashSet<Account> accounts)
    {
        _accounts = accounts;
    }

    /**
     * @return возвращает все счета
     */
    @Override
    public HashSet<Account> getAllAccounts()
    {
        return _accounts;
    }

    /**
     * @param id айди счета
     * @return счет
     */
    @Override
    public @Nullable Account findAccount(UUID id) {
        return _accounts.stream()
                .filter(account -> account.get_id().equals(id))
                .findFirst()
                .orElse(null);
    }


    /**
     * @param account счет, который надо добавить
     */
    @Override
    public void addNewAccount(Account account)
    {
        _accounts.add(account);
    }
}

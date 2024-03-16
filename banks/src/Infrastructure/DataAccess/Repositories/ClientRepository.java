package Infrastructure.DataAccess.Repositories;

import application.abstractions.IClientRepository;
import domain.models.Bank;
import domain.models.Client;
import domain.models.notofications.Notification;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.UUID;

/**
 * репозиторий клиентов
 */
public class ClientRepository implements IClientRepository
{
    private final HashSet<Client> _clients;

    public ClientRepository(HashSet<Client> clients)
    {
        _clients = clients;
    }

    /**
     * @return все клиенты
     */
    @Override
    public HashSet<Client> getAllClients()
    {
        return _clients;
    }

    /**
     * @param id айди клиента
     * @return клиент
     */
    @Override
    public @Nullable Client findClient(UUID id) {
        return _clients.stream()
                .filter(client -> client.get_id().equals(id))
                .findFirst()
                .orElse(null);
    }


    /**
     * @param client клиент, которого надо добавить
     */
    public void addNewClient(Client client)
    {
        _clients.add(client);
    }

    public void update(UUID clientId, Client client) {
        Client currentClient = findClient(clientId);
        _clients.remove(currentClient);
        _clients.add(client);
    }

}

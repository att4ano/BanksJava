package Infrastructure.DataAccess.Repositories;

import application.abstractions.IClientRepository;
import domain.exceptions.NotFoundException;
import domain.models.Bank;
import domain.models.Client;
import domain.models.notofications.Notification;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * репозиторий клиентов
 */
public class ClientRepository implements IClientRepository
{
    private final Set<Client> clients;

    public ClientRepository(HashSet<Client> clients)
    {
        this.clients = clients;
    }

    /**
     * @return все клиенты
     */
    @Override
    public Set<Client> getAllClients()
    {
        return clients;
    }

    /**
     * @param id айди клиента
     * @return клиент
     */
    @Override
    public @Nullable Client findClient(UUID id) {
        return clients.stream()
                .filter(client -> client.getId().equals(id))
                .findFirst()
                .orElse(null);
    }


    /**
     * @param client клиент, которого надо добавить
     */
    public void addNewClient(Client client)
    {
        clients.add(client);
    }

    public void update(UUID clientId, Client client) throws NotFoundException {
        Client currentClient = findClient(clientId);
        if (currentClient == null) {
            throw NotFoundException.clientNotFound();
        }
        currentClient.setName(client.getName());
        currentClient.setSurname(client.getSurname());
        currentClient.setAccounts(client.getAccounts());
        currentClient.setAddress(client.getAddress());
        currentClient.setPassportData(client.getPassportData());
    }

}

package application.abstractions;

import domain.models.Client;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.UUID;

public interface IClientRepository
{
    HashSet<Client> getAllClients();

    @Nullable Client findClient(UUID id);

    void addNewClient(Client client);

    void update(UUID clientId, Client client);
}

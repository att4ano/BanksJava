package application.abstractions;

import domain.exceptions.NotFoundException;
import domain.models.Client;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public interface IClientRepository
{
    Set<Client> getAllClients();

    @Nullable Client findClient(UUID id);

    void addNewClient(Client client);

    void update(UUID clientId, Client client) throws NotFoundException;
}

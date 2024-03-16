package domain.interfaces;

import domain.models.Client;

public interface ICreateNewClientService {
    Client createNewClient(String name, String surname, String address, String passportData);
}

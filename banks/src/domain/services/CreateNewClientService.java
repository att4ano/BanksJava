package domain.services;

import domain.interfaces.ICreateNewClientService;
import domain.models.CentralBank;
import domain.models.Client;

import java.util.UUID;

/**
 * сервис, создающйи клиентов
 */
public class CreateNewClientService implements ICreateNewClientService {
    private final CentralBank _centralBank;

    public CreateNewClientService(CentralBank centralBank) {
        _centralBank = centralBank;
    }

    /**
     * @param name имя клиента
     * @param surname фамилия клиента
     * @param address адрес клиента
     * @param passportData пасспортнве данные клиента
     * @return созданный клиент
     */
    @Override
    public Client createNewClient(String name, String surname, String address, String passportData) {
        return _centralBank.createNewClient(name, surname, address, passportData);
    }
}

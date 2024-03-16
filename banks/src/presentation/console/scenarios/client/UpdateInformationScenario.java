package presentation.console.scenarios.client;

import application.contracts.IClientService;
import application.result.ServiceResult;
import presentation.console.Scenario;

import java.util.Scanner;

/**
 * провайдер сценария обновления информации
 */
public class UpdateInformationScenario extends Scenario {
    private final IClientService _clientService;

    public UpdateInformationScenario(IClientService clientService) {
        super("Update client information");
        _clientService = clientService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        System.out.println("What do you want to update");
        Scanner scanner = new Scanner(System.in);
        String infoType = scanner.nextLine();
        String newInfo = scanner.nextLine();
        ServiceResult serviceResult = null;

        switch (infoType) {
            case "Passport" -> serviceResult = _clientService.updatePassportData(newInfo);
            case "Address" -> serviceResult = _clientService.updateAddress(newInfo);
        }

        if (serviceResult != null) {
            System.out.println(serviceResult.get_message());
        } else {
            System.out.println("This info of client does not exists");
        }

        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}


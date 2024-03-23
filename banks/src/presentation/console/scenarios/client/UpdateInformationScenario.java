package presentation.console.scenarios.client;

import application.contracts.IClientService;
import application.result.ServiceResult;
import presentation.console.Scenario;

import java.util.Scanner;

/**
 * провайдер сценария обновления информации
 */
public class UpdateInformationScenario extends Scenario {
    private final IClientService clientService;

    public UpdateInformationScenario(IClientService clientService) {
        super("Update client information");
        this.clientService = clientService;
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
            case "Passport" -> serviceResult = clientService.updatePassportData(newInfo);
            case "Address" -> serviceResult = clientService.updateAddress(newInfo);
        }

        if (serviceResult != null) {
            System.out.println(serviceResult.getMessage());
        } else {
            System.out.println("This info of client does not exists");
        }

        scanner.nextLine();
    }
}


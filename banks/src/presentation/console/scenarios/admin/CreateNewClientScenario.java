package presentation.console.scenarios.admin;

import application.contracts.ICentralBankService;
import application.result.ServiceResult;
import presentation.console.Scenario;

import java.util.Scanner;

/**
 * Сценарий создания клиента
 */
public class CreateNewClientScenario extends Scenario {
    private final ICentralBankService _centralBankService;
    public CreateNewClientScenario(ICentralBankService centralBankService) {
        super("Create new client");
        _centralBankService = centralBankService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        System.out.println("Enter name, surname, address and passport data");
        Scanner scanner = new Scanner(System.in);

        String name = scanner.nextLine();
        String surname = scanner.nextLine();
        String address = scanner.nextLine();
        String passportData = scanner.nextLine();

        ServiceResult serviceResult = _centralBankService.createNewClient(name, surname, address, passportData);
        System.out.println(serviceResult.get_message());
        scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

package presentation.console.scenarios.admin;

import application.contracts.ICentralBankService;
import application.result.ServiceResult;
import domain.exceptions.AlreadyExistsException;
import presentation.console.Scenario;

import java.util.Scanner;

/**
 * Сценарий создания клиента
 */
public class CreateNewClientScenario extends Scenario {
    private final ICentralBankService centralBankService;
    public CreateNewClientScenario(ICentralBankService centralBankService) {
        super("Create new client");
        this.centralBankService = centralBankService;
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

        try {
            ServiceResult serviceResult = this.centralBankService.createNewClient(name, surname, address, passportData);
            System.out.println(serviceResult.getMessage());
        } catch (AlreadyExistsException exception) {
            System.out.println(exception.toString());
        }

        scanner.nextLine();
    }
}

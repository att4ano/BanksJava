package presentation.console.scenarios.admin;

import application.contracts.ICentralBankService;
import application.result.ServiceResult;
import domain.exceptions.AlreadyExistsException;
import presentation.console.Scenario;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * сценарий создания банка
 */
public class CreateNewBankScenario extends Scenario {
    private final ICentralBankService centralBankService;

    public CreateNewBankScenario(ICentralBankService centralBankService) {
        super("Create new bank");
        this.centralBankService = centralBankService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        System.out.println("Enter the name, interest, commission and limit");
        Scanner scanner = new Scanner(System.in);

        String name = scanner.nextLine();
        double interest = Double.parseDouble(scanner.nextLine());
        double commission = Double.parseDouble(scanner.nextLine());
        BigDecimal limit = new BigDecimal(scanner.nextLine());

        try {
            ServiceResult serviceResult = centralBankService.createNewBank(name, interest, commission, limit);
            System.out.println(serviceResult.getMessage());
        } catch (AlreadyExistsException exception) {
            System.out.println(exception.toString());
        }

        scanner.nextLine();
    }
}

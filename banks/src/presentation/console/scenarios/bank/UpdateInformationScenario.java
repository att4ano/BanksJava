package presentation.console.scenarios.bank;

import application.contracts.IBankService;
import application.result.ServiceResult;
import presentation.console.Scenario;

import java.util.Scanner;

/**
 * Сценарий обновления информации
 */
public class UpdateInformationScenario extends Scenario {
    private final IBankService _bankService;
    public UpdateInformationScenario(IBankService bankService) {
        super("Update information");
        _bankService = bankService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        System.out.println("What do you want to update");
        Scanner scanner = new Scanner(System.in);
        String info = scanner.nextLine();
        double newValue = scanner.nextDouble();
        ServiceResult serviceResult = null;

        if (info.equals("Interest")) {
            serviceResult = _bankService.updateInterest(newValue);
        } else if (info.equals("Commission")) {
            serviceResult = _bankService.updateCommission(newValue);
        }
        if (serviceResult != null)
            System.out.println(serviceResult.get_message());

        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

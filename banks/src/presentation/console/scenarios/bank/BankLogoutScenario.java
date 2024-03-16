package presentation.console.scenarios.bank;

import application.contracts.IBankService;
import application.result.ServiceResult;
import presentation.console.Scenario;

import java.util.Scanner;

/**
 * сценарий выхода из банка
 */
public class BankLogoutScenario extends Scenario {
    private final IBankService _bankService;
    public BankLogoutScenario(IBankService bankService) {
        super("Logout");
        _bankService = bankService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        ServiceResult serviceResult = _bankService.logout();
        Scanner scanner = new Scanner(System.in);
        System.out.println(serviceResult.get_message());

        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

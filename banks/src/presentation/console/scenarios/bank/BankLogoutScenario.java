package presentation.console.scenarios.bank;

import application.contracts.IBankService;
import application.result.ServiceResult;
import presentation.console.Scenario;

import java.util.Scanner;

/**
 * сценарий выхода из банка
 */
public class BankLogoutScenario extends Scenario {
    private final IBankService bankService;
    public BankLogoutScenario(IBankService bankService) {
        super("Logout");
        this.bankService = bankService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        ServiceResult serviceResult = bankService.logout();
        Scanner scanner = new Scanner(System.in);
        System.out.println(serviceResult.getMessage());

        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

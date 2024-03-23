package presentation.console.scenarios.admin;

import application.contracts.ICentralBankService;
import application.result.ServiceResult;
import presentation.console.Scenario;

import java.util.Scanner;

/**
 * Суенарий выхода из аккаунта за админа
 */
public class AdminLogoutScenario extends Scenario {
    private final ICentralBankService centralBankService;
    public AdminLogoutScenario(ICentralBankService centralBankService) {
        super("Logout");
        this.centralBankService = centralBankService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        ServiceResult serviceResult = centralBankService.logout();
        Scanner scanner = new Scanner(System.in);
        System.out.println(serviceResult.getMessage());

        scanner.nextLine();
    }
}

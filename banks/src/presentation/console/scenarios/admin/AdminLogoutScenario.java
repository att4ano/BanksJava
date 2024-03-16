package presentation.console.scenarios.admin;

import application.contracts.ICentralBankService;
import application.result.ServiceResult;
import presentation.console.Scenario;

import java.util.Scanner;

/**
 * Суенарий выхода из аккаунта за админа
 */
public class AdminLogoutScenario extends Scenario {
    private final ICentralBankService _centralBankService;
    public AdminLogoutScenario(ICentralBankService centralBankService) {
        super("Logout");
        _centralBankService = centralBankService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        ServiceResult serviceResult = _centralBankService.logout();
        Scanner scanner = new Scanner(System.in);
        System.out.println(serviceResult.get_message());

        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

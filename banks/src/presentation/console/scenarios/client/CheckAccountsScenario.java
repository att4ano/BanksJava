package presentation.console.scenarios.client;

import application.contracts.IClientService;
import domain.models.accounts.Account;
import presentation.console.Scenario;

import java.util.List;
import java.util.Scanner;

/**
 * Сценарий просмотра аккаунтов
 */
public class CheckAccountsScenario extends Scenario {
    private final IClientService _clientService;
    public CheckAccountsScenario(IClientService clientService) {
        super("Check accounts");
        _clientService = clientService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        List<Account> accounts = _clientService.checkClientAccounts();

        if (accounts != null)
            for (var account : accounts) {
                System.out.println(account.toString());
            }

        Scanner scanner = new Scanner(System.in);

        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

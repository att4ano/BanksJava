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
    private final IClientService clientService;
    public CheckAccountsScenario(IClientService clientService) {
        super("Check accounts");
        this.clientService = clientService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        List<Account> accounts = this.clientService.checkClientAccounts();

        if (accounts != null)
            for (var account : accounts) {
                System.out.println(account.toString());
            }

        Scanner scanner = new Scanner(System.in);

        scanner.nextLine();
    }
}

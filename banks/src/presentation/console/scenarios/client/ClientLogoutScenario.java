package presentation.console.scenarios.client;

import application.contracts.IClientService;
import application.result.ServiceResult;
import presentation.console.Scenario;

import java.util.Scanner;

/**
 * сценарий выхода из клиента
 */
public class ClientLogoutScenario extends Scenario {
    private final IClientService _clientService;
    public ClientLogoutScenario(IClientService clientService) {
        super("Logout");
        _clientService = clientService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        ServiceResult serviceResult = _clientService.logout();
        Scanner scanner = new Scanner(System.in);
        System.out.println(serviceResult.get_message());

        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

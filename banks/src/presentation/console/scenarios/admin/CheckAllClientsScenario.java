package presentation.console.scenarios.admin;

import application.contracts.ICentralBankService;
import domain.models.Bank;
import domain.models.Client;
import presentation.console.Scenario;

import java.util.List;
import java.util.Scanner;

/**
 * сцкнарий просмотра всех клиентов
 */
public class CheckAllClientsScenario extends Scenario {
    private final ICentralBankService _centralBankService;

    public CheckAllClientsScenario(ICentralBankService centralBankService) {
        super("Check all clients");
        _centralBankService = centralBankService;
    }

    /**
     * звупуск сценария
     */
    @Override
    public void run() {
        List<Client> clients = _centralBankService.checkAllClients();

        if (clients != null)
            for (var client : clients) {
                System.out.println(client.toString());
            }

        Scanner scanner = new Scanner(System.in);

        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

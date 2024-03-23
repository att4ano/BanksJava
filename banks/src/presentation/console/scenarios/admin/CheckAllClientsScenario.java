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
    private final ICentralBankService centralBankService;

    public CheckAllClientsScenario(ICentralBankService centralBankService) {
        super("Check all clients");
        this.centralBankService = centralBankService;
    }

    /**
     * звупуск сценария
     */
    @Override
    public void run() {
        List<Client> clients = centralBankService.checkAllClients();

        if (clients != null)
            for (var client : clients) {
                System.out.println(client.toString());
            }

        Scanner scanner = new Scanner(System.in);

        scanner.nextLine();
    }
}

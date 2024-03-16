package presentation.console.scenarios.client;

import application.contracts.IClientService;
import application.result.ServiceResult;
import presentation.console.Scenario;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;

/**
 * сценариый добавлени денег
 */
public class AddMoneyScenario extends Scenario {
    private final IClientService _clientService;

    public AddMoneyScenario(IClientService clientService) {
        super("Add money to account");
        _clientService = clientService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        System.out.println("Enter account id and money amount");
        Scanner scanner = new Scanner(System.in);

        UUID id = UUID.fromString(scanner.nextLine());
        BigDecimal moneyAmount = scanner.nextBigDecimal();
        ServiceResult serviceResult = _clientService.addMoney(id, moneyAmount);
        System.out.println(serviceResult.get_message());

        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

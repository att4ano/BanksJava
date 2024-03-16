package presentation.console.scenarios.client;

import application.contracts.IClientService;
import application.result.ServiceResult;
import presentation.console.Scenario;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;

/**
 * сценарий перевода денег
 */
public class TransferMoneyScenario extends Scenario {
    private final IClientService _clientService;

    public TransferMoneyScenario(IClientService clientService) {
        super("Transfer money");
        _clientService = clientService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        System.out.println("Enter account from, account to and money amount");
        Scanner scanner = new Scanner(System.in);

        UUID fromId = UUID.fromString(scanner.nextLine());
        UUID toId = UUID.fromString(scanner.nextLine());
        BigDecimal moneyAmount = scanner.nextBigDecimal();

        ServiceResult serviceResult = _clientService.transferMoney(fromId, toId, moneyAmount);
        System.out.println(serviceResult.get_message());

        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

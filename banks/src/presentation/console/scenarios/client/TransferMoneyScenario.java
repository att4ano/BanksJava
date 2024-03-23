package presentation.console.scenarios.client;

import application.contracts.IClientService;
import application.result.ServiceResult;
import domain.exceptions.NotFoundException;
import presentation.console.Scenario;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;

/**
 * сценарий перевода денег
 */
public class TransferMoneyScenario extends Scenario {
    private final IClientService clientService;

    public TransferMoneyScenario(IClientService clientService) {
        super("Transfer money");
        this.clientService = clientService;
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

        try {
            ServiceResult serviceResult = clientService.transferMoney(fromId, toId, moneyAmount);
            System.out.println(serviceResult.getMessage());
        } catch (NotFoundException exception) {
            System.out.println(exception.toString());
        }

        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

package presentation.console.scenarios.client;

import application.contracts.IClientService;
import application.result.ServiceResult;
import domain.exceptions.NotFoundException;
import presentation.console.Scenario;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;

/**
 * сценарий снятия денег
 */
public class WithdrawMoneyScenario extends Scenario {
    private final IClientService clientService;

    public WithdrawMoneyScenario(IClientService clientService) {
        super("Withdraw money to account");
        this.clientService = clientService;
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
        try {
            ServiceResult serviceResult = clientService.withdrawMoney(id, moneyAmount);
            System.out.println(serviceResult.getMessage());
        } catch (NotFoundException exception) {
            System.out.println(exception.toString());
        }

        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

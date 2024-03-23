package presentation.console.scenarios.client;

import application.contracts.IClientService;
import application.result.ServiceResult;
import presentation.console.Scenario;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * сценрий создания счета
 */
public class CreateNewAccountScenario extends Scenario {
    private final IClientService clientService;
    protected CreateNewAccountScenario(IClientService clientService) {
        super("Create account");
        this.clientService = clientService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        System.out.println("Which type of account do you want to create and in which bank");
        Scanner scanner = new Scanner(System.in);
        String accountType = scanner.nextLine();
        String bankName = scanner.nextLine();
        ServiceResult serviceResult = null;

        switch (accountType) {
            case "Debit" -> serviceResult = clientService.createDebitAccount(bankName);
            case "Deposit" -> {
                Integer term = Integer.valueOf(scanner.nextLine());
                BigDecimal noneyAmount = new BigDecimal(scanner.nextLine());
                serviceResult = clientService.createDepositAccount(bankName, term, noneyAmount);
            }
            case "Credit" -> {
                BigDecimal noneyAmount = new BigDecimal(scanner.nextLine());
                serviceResult = clientService.createCreditAccount(bankName, noneyAmount);
            }
        }

        if (serviceResult != null) {
            System.out.println(serviceResult.getMessage());
        } else {
            System.out.println("This type of account does not exists");
        }

        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

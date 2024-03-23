package presentation.console.scenarios.client;

import application.contracts.IClientService;
import application.result.ServiceResult;
import domain.exceptions.NotFoundException;
import presentation.console.Scenario;

import java.util.Scanner;

public class SubscribeToBankScenario extends Scenario {
    private final IClientService clientService;

    public SubscribeToBankScenario(IClientService clientService) {
        super("Subscribe to bank");
        this.clientService = clientService;
    }

    @Override
    public void run() {
        System.out.println("Enter the bank name");
        Scanner scanner = new Scanner(System.in);

        String bankName = scanner.nextLine();

        try {
            ServiceResult result = clientService.subscribe(bankName);
            System.out.println(result.getMessage());
        } catch (NotFoundException exception) {
            System.out.println(exception.toString());
        }

        scanner.nextLine();
    }
}

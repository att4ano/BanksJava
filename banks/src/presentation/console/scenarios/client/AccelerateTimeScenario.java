package presentation.console.scenarios.client;

import application.contracts.IClientService;
import domain.exceptions.NotFoundException;
import presentation.console.Scenario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

/**
 * сценарий ускорения времени
 */
public class AccelerateTimeScenario extends Scenario {
    private final IClientService clientService;
    public AccelerateTimeScenario(IClientService clientService) {
        super("Accelerate time");
        this.clientService = clientService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        System.out.println("Enter date and account id");
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        LocalDate date = LocalDate.parse(input);
        UUID id = UUID.fromString(scanner.nextLine());

        try {
            BigDecimal money = clientService.accelerateTime(date, id);
            if (money != null)
                System.out.println(money);
        } catch (NotFoundException exception) {
            System.out.println(exception.toString());
        }

        scanner.nextLine();
    }
}

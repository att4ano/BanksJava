package presentation.console.scenarios.client;

import application.contracts.IClientService;
import presentation.console.Scenario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

/**
 * сценарий ускорения времени
 */
public class AccelerateTimeScenario extends Scenario {
    private final IClientService _clientService;
    public AccelerateTimeScenario(IClientService clientService) {
        super("Accelerate time");
        _clientService = clientService;
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

        BigDecimal money = _clientService.accelerateTime(date, id);
        if (money != null)
            System.out.println(money);

        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

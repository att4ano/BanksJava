package presentation.console.scenarios.admin;

import application.contracts.ICentralBankService;
import domain.models.Bank;
import domain.models.notofications.Notification;
import presentation.console.Scenario;

import java.util.List;
import java.util.Scanner;

/**
 * сценарий просмотра всех банков
 */
public class CheckAllBanksScenario extends Scenario {
    private final ICentralBankService _centralBankService;

    public CheckAllBanksScenario(ICentralBankService centralBankService) {
        super("Check all banks");
        _centralBankService = centralBankService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        List<Bank> banks = _centralBankService.checkAllBanks();

        if (banks != null)
            for (var bank : banks) {
                System.out.println(bank.toString());
            }

        Scanner scanner = new Scanner(System.in);

        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

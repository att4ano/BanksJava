package presentation.console.scenarios.admin;

import application.contracts.ICentralBankService;
import application.result.ServiceResult;
import presentation.console.Scenario;

import java.util.Scanner;

/**
 * сценарий выплат
 */
public class MakePaymentScenario extends Scenario {
    private final ICentralBankService _centralBankService;
    public MakePaymentScenario(ICentralBankService centralBankService) {
        super("Make interest payment");
        _centralBankService = centralBankService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        ServiceResult serviceResult = _centralBankService.makePayment();
        Scanner scanner = new Scanner(System.in);
        System.out.println(serviceResult.get_message());

        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

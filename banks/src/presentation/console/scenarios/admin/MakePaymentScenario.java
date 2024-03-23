package presentation.console.scenarios.admin;

import application.contracts.ICentralBankService;
import application.result.ServiceResult;
import presentation.console.Scenario;

import java.util.Scanner;

/**
 * сценарий выплат
 */
public class MakePaymentScenario extends Scenario {
    private final ICentralBankService centralBankService;
    public MakePaymentScenario(ICentralBankService centralBankService) {
        super("Make interest payment");
        this.centralBankService = centralBankService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        ServiceResult serviceResult = centralBankService.makePayment();
        Scanner scanner = new Scanner(System.in);
        System.out.println(serviceResult.getMessage());

        scanner.nextLine();
    }
}

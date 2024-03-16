package presentation.console.scenarios.admin;

import application.contracts.ICentralBankService;
import application.result.ServiceResult;
import presentation.console.Scenario;

import java.util.Scanner;
import java.util.UUID;

/**
 * Сценарий отчмены транзакции
 */
public class UndoTransactionScenario extends Scenario {
    private final ICentralBankService _centralBankService;
    public UndoTransactionScenario(ICentralBankService centralBankService) {
        super("Undo transaction");
        _centralBankService = centralBankService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        System.out.println("Enter the id of transaction");
        Scanner scanner = new Scanner(System.in);

        UUID id = UUID.fromString(scanner.nextLine());
        ServiceResult serviceResult = _centralBankService.undoTransaction(id);
        System.out.println(serviceResult.get_message());

        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

package presentation.console.scenarios.admin;

import application.contracts.ICentralBankService;
import application.result.ServiceResult;
import domain.exceptions.NotFoundException;
import presentation.console.Scenario;

import java.util.Scanner;
import java.util.UUID;

/**
 * Сценарий отчмены транзакции
 */
public class UndoTransactionScenario extends Scenario {
    private final ICentralBankService centralBankService;
    public UndoTransactionScenario(ICentralBankService centralBankService) {
        super("Undo transaction");
        this.centralBankService = centralBankService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        System.out.println("Enter the id of transaction");
        Scanner scanner = new Scanner(System.in);

        UUID id = UUID.fromString(scanner.nextLine());
        try {
            ServiceResult serviceResult = centralBankService.undoTransaction(id);
            System.out.println(serviceResult.getMessage());
        } catch (NotFoundException exception) {
            System.out.println(exception.toString());
        }

        scanner.nextLine();
    }
}

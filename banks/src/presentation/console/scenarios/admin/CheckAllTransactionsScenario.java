package presentation.console.scenarios.admin;

import application.contracts.ICentralBankService;
import domain.interfaces.Transaction;
import presentation.console.Scenario;

import java.util.List;
import java.util.Scanner;

/**
 * сценарий просмотра всех транзакций
 */
public class CheckAllTransactionsScenario extends Scenario {
    private final ICentralBankService _centralBankService;
    public CheckAllTransactionsScenario(ICentralBankService centralBankService) {
        super("Check all transactions");
        _centralBankService = centralBankService;
    }

    /**
     * запуск всех сценариев
     */
    @Override
    public void run() {
        List<Transaction> transactions = _centralBankService.checkAllTransactions();

        if (transactions != null)
            for (var transaction : transactions) {
                System.out.println(transaction.toString());
            }

        Scanner scanner = new Scanner(System.in);

        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

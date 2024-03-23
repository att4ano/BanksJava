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
    private final ICentralBankService centralBankService;
    public CheckAllTransactionsScenario(ICentralBankService centralBankService) {
        super("Check all transactions");
        this.centralBankService = centralBankService;
    }

    /**
     * запуск всех сценариев
     */
    @Override
    public void run() {
        List<Transaction> transactions = this.centralBankService.checkAllTransactions();

        if (transactions != null)
            for (var transaction : transactions) {
                System.out.println(transaction.toString());
            }

        Scanner scanner = new Scanner(System.in);

        scanner.nextLine();
    }
}

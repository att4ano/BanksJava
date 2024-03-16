package presentation.console.scenarios.login;

import application.contracts.IBankService;
import application.cotracts.LoginResult;
import presentation.console.Scenario;

import java.util.Scanner;

public class LoginAsBankScenario extends Scenario {
    private final IBankService _bankService;
    public LoginAsBankScenario(IBankService bankService) {
        super("bank login");

        _bankService = bankService;
    }

    @Override
    public void run() {
        System.out.println("Enter the bank name");
        Scanner scanner = new Scanner(System.in);

        String bankName = scanner.nextLine();
        LoginResult loginResult = _bankService.login(bankName);

        System.out.println(loginResult.getMessage());
        String input = scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

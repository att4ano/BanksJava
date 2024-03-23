package presentation.console.scenarios.login;

import application.contracts.IBankService;
import application.result.LoginResult;
import presentation.console.Scenario;

import java.util.Scanner;

public class LoginAsBankScenario extends Scenario {
    private final IBankService bankService;
    public LoginAsBankScenario(IBankService bankService) {
        super("bank login");

        this.bankService = bankService;
    }

    @Override
    public void run() {
        System.out.println("Enter the bank name");
        Scanner scanner = new Scanner(System.in);

        String bankName = scanner.nextLine();
        LoginResult loginResult = bankService.login(bankName);

        System.out.println(loginResult.toString());
        scanner.nextLine();
    }
}

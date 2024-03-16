package presentation.console.scenarios.login;

import application.contracts.ICentralBankService;
import application.cotracts.LoginResult;
import presentation.console.Scenario;

import java.util.Scanner;

public class LoginAsAdminScenario extends Scenario {
    private final ICentralBankService _centralBankService;
    public LoginAsAdminScenario(ICentralBankService centralBankService) {
        super("Central bank login");
        _centralBankService = centralBankService;
    }

    @Override
    public void run() {
        System.out.println("Enter the password");
        Scanner scanner = new Scanner(System.in);

        String password = scanner.nextLine();
        LoginResult loginResult = _centralBankService.login(password);

        System.out.println(loginResult.getMessage());
        String input = scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}

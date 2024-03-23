package presentation.console.scenarios.login;

import application.contracts.ICentralBankService;
import application.result.LoginResult;
import presentation.console.Scenario;

import java.util.Scanner;

public class LoginAsAdminScenario extends Scenario {
    private final ICentralBankService centralBankService;
    public LoginAsAdminScenario(ICentralBankService centralBankService) {
        super("Central bank login");
        this.centralBankService = centralBankService;
    }

    @Override
    public void run() {
        System.out.println("Enter the password");
        Scanner scanner = new Scanner(System.in);

        String password = scanner.nextLine();
        LoginResult loginResult = centralBankService.login(password);

        System.out.println(loginResult.toString());
        String input = scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}

package presentation.console.scenarios.login;

import application.contracts.IClientService;
import application.cotracts.LoginResult;
import presentation.console.Scenario;

import java.util.Scanner;

public class LoginAsClientScenario extends Scenario {
    private final IClientService _clientService;
    public LoginAsClientScenario(IClientService clientService) {
        super("Client login");
        _clientService = clientService;
    }

    @Override
    public void run() {
        System.out.println("Enter the name and surname");
        Scanner scanner = new Scanner(System.in);

        String name = scanner.nextLine();
        String surname = scanner.nextLine();
        LoginResult loginResult = _clientService.login(name, surname);

        System.out.println(loginResult.getMessage());
        String input = scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}

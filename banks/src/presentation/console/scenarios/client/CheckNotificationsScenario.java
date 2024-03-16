package presentation.console.scenarios.client;

import application.contracts.IClientService;
import domain.models.notofications.Notification;
import presentation.console.Scenario;

import java.util.List;
import java.util.Scanner;

/**
 * сценарий просмотра всех уведомлений
 */
public class CheckNotificationsScenario extends Scenario {
    private final IClientService _clientService;
    public CheckNotificationsScenario(IClientService clientService) {
        super("Check notifications");
        _clientService = clientService;
    }

    /**
     * запуск сценария
     */
    @Override
    public void run() {
        List<Notification> notifications = _clientService.checkClientNotifications();

        if (notifications != null)
            for (var notification : notifications) {
                System.out.println(notification.toString());
            }

        Scanner scanner = new Scanner(System.in);

        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

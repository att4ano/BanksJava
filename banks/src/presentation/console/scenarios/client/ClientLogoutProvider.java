package presentation.console.scenarios.client;

import application.application.CurrentSession;
import application.contracts.IClientService;
import application.contracts.ICurrentUserManager;
import org.jetbrains.annotations.Nullable;
import presentation.console.IScenarioProvider;
import presentation.console.Scenario;

/**
 * провайдер сценария выхода за клиента
 */
public class ClientLogoutProvider implements IScenarioProvider {
    private final IClientService clientService;
    private final ICurrentUserManager currentUserManager;

    public ClientLogoutProvider(IClientService clientService, ICurrentUserManager currentUserManager) {
        this.clientService = clientService;
        this.currentUserManager = currentUserManager;
    }

    /**
     * @param scenario сценарий который надо проверить
     * @return сценарий, если его можно выдать для данного состояния
     */
    @Override
    public Scenario tryGetScenario(@Nullable Scenario scenario) {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession))
            return null;

        scenario = new ClientLogoutScenario(clientService);
        return scenario;
    }
}

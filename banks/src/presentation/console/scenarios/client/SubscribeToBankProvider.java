package presentation.console.scenarios.client;

import application.application.CurrentSession;
import application.contracts.IClientService;
import application.contracts.ICurrentUserManager;
import org.jetbrains.annotations.Nullable;
import presentation.console.IScenarioProvider;
import presentation.console.Scenario;

public class SubscribeToBankProvider implements IScenarioProvider {
    private final IClientService clientService;
    private final ICurrentUserManager currentUserManager;

    public SubscribeToBankProvider(IClientService clientService, ICurrentUserManager currentUserManager) {
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

        scenario = new SubscribeToBankScenario(clientService);
        return scenario;
    }
}

package presentation.console.scenarios.client;

import application.application.CurrentSession;
import application.contracts.IClientService;
import application.contracts.ICurrentUserManager;
import org.jetbrains.annotations.Nullable;
import presentation.console.IScenarioProvider;
import presentation.console.Scenario;

/**
 * провайдер сценария ускорения времени
 */
public class AddMoneyProvider implements IScenarioProvider {
    private final IClientService _clientService;
    private final ICurrentUserManager _currentUserManager;

    public AddMoneyProvider(IClientService clientService, ICurrentUserManager currentUserManager) {
        _clientService = clientService;
        _currentUserManager = currentUserManager;
    }

    /**
     * @param scenario сценарий который надо проверить
     * @return сценарий, если его можно выдать для данного состояния
     */
    @Override
    public Scenario tryGetScenario(@Nullable Scenario scenario) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.ClientSession))
            return null;

        scenario = new AddMoneyScenario(_clientService);
        return scenario;
    }
}

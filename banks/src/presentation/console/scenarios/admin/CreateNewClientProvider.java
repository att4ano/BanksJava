package presentation.console.scenarios.admin;

import application.application.CurrentSession;
import application.contracts.ICentralBankService;
import application.contracts.ICurrentUserManager;
import org.jetbrains.annotations.Nullable;
import presentation.console.IScenarioProvider;
import presentation.console.Scenario;

/**
 * Провайдер сценария создания коиента
 */
public class CreateNewClientProvider implements IScenarioProvider {
    private final ICentralBankService _centralBankService;
    private final ICurrentUserManager _currentUserManager;

    public CreateNewClientProvider(ICentralBankService centralBankService, ICurrentUserManager currentUserManager) {
        _centralBankService = centralBankService;
        _currentUserManager = currentUserManager;
    }

    /**
     * @param scenario сценарий который надо проверить
     * @return сценарий, если его можно выдать для данного состояния
     */
    @Override
    public Scenario tryGetScenario(@Nullable Scenario scenario) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.CentralBankSession))
            return null;

        scenario = new CreateNewClientScenario(_centralBankService);
        return scenario;
    }
}

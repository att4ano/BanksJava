package presentation.console.scenarios.login;

import application.application.CurrentSession;
import application.contracts.IBankService;
import application.contracts.ICurrentUserManager;
import org.jetbrains.annotations.Nullable;
import presentation.console.IScenarioProvider;
import presentation.console.Scenario;

public class LoginAsBankProvider implements IScenarioProvider {
    private final IBankService _bankService;
    private final ICurrentUserManager _currentUserManager;

    public LoginAsBankProvider(IBankService bankService, ICurrentUserManager currentUserManager) {
        _bankService = bankService;
        _currentUserManager = currentUserManager;
    }

    @Override
    public Scenario tryGetScenario(@Nullable Scenario scenario) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.UnauthorizedSession))
            return null;

        scenario = new LoginAsBankScenario(_bankService);
        return scenario;
    }
}

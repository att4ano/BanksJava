package presentation.console.scenarios.login;

import application.application.CurrentSession;
import application.contracts.ICentralBankService;
import application.contracts.ICurrentUserManager;
import org.jetbrains.annotations.Nullable;
import presentation.console.IScenarioProvider;
import presentation.console.Scenario;

public class LoginAsAdminProvider implements IScenarioProvider {
    private final ICentralBankService _centralBankService;
    private final ICurrentUserManager _currentUserManager;

    public LoginAsAdminProvider(ICentralBankService centralBankService, ICurrentUserManager currentUserManager) {
        _centralBankService = centralBankService;
        _currentUserManager = currentUserManager;
    }


    @Override
    public Scenario tryGetScenario(@Nullable Scenario scenario) {
        if (!(_currentUserManager.getCurrentSession() instanceof CurrentSession.UnauthorizedSession))
            return null;

        scenario = new LoginAsAdminScenario(_centralBankService);
        return scenario;
    }
}

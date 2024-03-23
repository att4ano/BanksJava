package presentation.console.scenarios.login;

import application.application.CurrentSession;
import application.contracts.ICentralBankService;
import application.contracts.ICurrentUserManager;
import org.jetbrains.annotations.Nullable;
import presentation.console.IScenarioProvider;
import presentation.console.Scenario;

public class LoginAsAdminProvider implements IScenarioProvider {
    private final ICentralBankService centralBankService;
    private final ICurrentUserManager currentUserManager;

    public LoginAsAdminProvider(ICentralBankService centralBankService, ICurrentUserManager currentUserManager) {
        this.centralBankService = centralBankService;
        this.currentUserManager = currentUserManager;
    }


    @Override
    public Scenario tryGetScenario(@Nullable Scenario scenario) {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.UnauthorizedSession))
            return null;

        scenario = new LoginAsAdminScenario(centralBankService);
        return scenario;
    }
}

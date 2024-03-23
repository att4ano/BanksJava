package presentation.console.scenarios.login;

import application.application.CurrentSession;
import application.contracts.IBankService;
import application.contracts.ICurrentUserManager;
import org.jetbrains.annotations.Nullable;
import presentation.console.IScenarioProvider;
import presentation.console.Scenario;

public class LoginAsBankProvider implements IScenarioProvider {
    private final IBankService bankService;
    private final ICurrentUserManager currentUserManager;

    public LoginAsBankProvider(IBankService bankService, ICurrentUserManager currentUserManager) {
        this.bankService = bankService;
        this.currentUserManager = currentUserManager;
    }

    @Override
    public Scenario tryGetScenario(@Nullable Scenario scenario) {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.UnauthorizedSession))
            return null;

        scenario = new LoginAsBankScenario(bankService);
        return scenario;
    }
}

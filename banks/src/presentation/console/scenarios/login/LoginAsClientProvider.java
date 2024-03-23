package presentation.console.scenarios.login;

import application.application.CurrentSession;
import application.contracts.IClientService;
import application.contracts.ICurrentUserManager;
import org.jetbrains.annotations.Nullable;
import presentation.console.IScenarioProvider;
import presentation.console.Scenario;

public class LoginAsClientProvider implements IScenarioProvider {
    private final IClientService clientService;
    private final ICurrentUserManager currentUserManager;

    public LoginAsClientProvider(IClientService clientService, ICurrentUserManager currentUserManager) {
        this.clientService = clientService;
        this.currentUserManager = currentUserManager;
    }

    @Override
    public Scenario tryGetScenario(@Nullable Scenario scenario) {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.UnauthorizedSession))
            return null;

        scenario = new LoginAsClientScenario(clientService);
        return scenario;
    }
}

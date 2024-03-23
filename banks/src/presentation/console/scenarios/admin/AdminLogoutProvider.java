package presentation.console.scenarios.admin;

import application.application.CurrentSession;
import application.contracts.IBankService;
import application.contracts.ICentralBankService;
import application.contracts.ICurrentUserManager;
import org.jetbrains.annotations.Nullable;
import presentation.console.IScenarioProvider;
import presentation.console.Scenario;
import presentation.console.scenarios.bank.BankLogoutScenario;

/**
 * Провайдер сценария выхода за админа
 */
public class AdminLogoutProvider implements IScenarioProvider {
    private final ICentralBankService centralBankService;
    private final ICurrentUserManager currentUserManager;

    public AdminLogoutProvider(ICentralBankService centralBankService, ICurrentUserManager currentUserManager) {
        this.centralBankService = centralBankService;
        this.currentUserManager = currentUserManager;
    }

    /**
     * @param scenario сценарий который надо проверить
     * @return сценарий, если его можно выдать для данного состояния
     */
    @Override
    public Scenario tryGetScenario(@Nullable Scenario scenario) {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.CentralBankSession))
            return null;

        scenario = new AdminLogoutScenario(centralBankService);
        return scenario;
    }
}

package presentation.console.scenarios.admin;

import application.application.CurrentSession;
import application.contracts.ICentralBankService;
import application.contracts.ICurrentUserManager;
import org.jetbrains.annotations.Nullable;
import presentation.console.IScenarioProvider;
import presentation.console.Scenario;

/**
 * провайдер просмотра всех админов
 */
public class CheckAllBanksProvider implements IScenarioProvider {
    private final ICentralBankService centralBankService;
    private final ICurrentUserManager currentUserManager;

    public CheckAllBanksProvider(ICentralBankService centralBankService, ICurrentUserManager currentUserManager) {
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

        scenario = new CheckAllBanksScenario(centralBankService);
        return scenario;
    }
}

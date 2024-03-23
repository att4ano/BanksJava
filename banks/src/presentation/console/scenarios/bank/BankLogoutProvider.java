package presentation.console.scenarios.bank;

import application.application.CurrentSession;
import application.contracts.IBankService;
import application.contracts.ICurrentUserManager;
import org.jetbrains.annotations.Nullable;
import presentation.console.IScenarioProvider;
import presentation.console.Scenario;

/**
 * провайдер сценария выхода из аккаунта банка
 */
public class BankLogoutProvider implements IScenarioProvider {
    private final IBankService bankService;
    private final ICurrentUserManager currentUserManager;

    public BankLogoutProvider(IBankService bankService, ICurrentUserManager currentUserManager) {
        this.bankService = bankService;
        this.currentUserManager = currentUserManager;
    }

    /**
     * @param scenario сценарий который надо проверить
     * @return сценарий, если его можно выдать для данного состояния
     */
    @Override
    public Scenario tryGetScenario(@Nullable Scenario scenario) {
        if (!(this.currentUserManager.getCurrentSession() instanceof CurrentSession.BankSession))
            return null;

        scenario = new BankLogoutScenario(bankService);
        return scenario;
    }
}

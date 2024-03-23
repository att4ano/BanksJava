package presentation.console.scenarios.bank;

import application.application.CurrentSession;
import application.contracts.IBankService;
import application.contracts.ICurrentUserManager;
import org.jetbrains.annotations.Nullable;
import presentation.console.IScenarioProvider;
import presentation.console.Scenario;
import presentation.console.scenarios.admin.CreateNewBankScenario;

/**
 * Провайдер запуска сценария обновления информации
 */
public class UpdateBankInformationProvider implements IScenarioProvider {

    private final IBankService bankService;
    private final ICurrentUserManager currentUserManager;

    public UpdateBankInformationProvider(IBankService bankService, ICurrentUserManager currentUserManager) {
        this.bankService = bankService;
        this.currentUserManager = currentUserManager;
    }


    /**
     * @param scenario сценарий который надо проверить
     * @return сценарий, если его можно выдать для данного состояния
     */
    @Override
    public Scenario tryGetScenario(@Nullable Scenario scenario) {
        if (!(currentUserManager.getCurrentSession() instanceof CurrentSession.BankSession))
            return null;

        scenario = new UpdateInformationScenario(bankService);
        return scenario;
    }
}

package presentation.console;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * класс, запускающий сценарий
 */
public class ScenarioRunner {
    private final List<IScenarioProvider> providers;

    public ScenarioRunner(List<IScenarioProvider> providers) {
        this.providers = providers;
    }

    private @NotNull List<Scenario> GetScenarios() {
        List<Scenario> currentScenarios = new ArrayList<>();
        for (var provider : providers) {
            Scenario scenario = null;
            scenario = provider.tryGetScenario(scenario);
            if (scenario != null)
                currentScenarios.add(scenario);
        }

        return currentScenarios;
    }

    /**
     * запуск всех сценариев
     */
    public void run() {
        List<Scenario> scenarios = GetScenarios();
        Scenario currentScenario = null;
        Scanner scanner = new Scanner(System.in);
        String input;

        for (var scenario : scenarios) {
            System.out.println(scenario.toString());
        }

        input = scanner.nextLine();
        for (var scenario : scenarios) {
            if (input.equals(scenario.toString())) {
                currentScenario = scenario;
                break;
            }
        }

        if (currentScenario != null) {
            currentScenario.run();
        }
    }
}

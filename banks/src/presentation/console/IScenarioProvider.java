package presentation.console;

import org.jetbrains.annotations.Nullable;

public interface IScenarioProvider
{
    Scenario tryGetScenario(@Nullable Scenario scenario);
}

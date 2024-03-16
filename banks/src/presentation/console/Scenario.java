package presentation.console;

/**
 * абстраутный класс сценария
 */
public abstract class Scenario
{
    protected final String _name;

    protected Scenario(String name)
    {
        _name = name;
    }

    /**
     * запуск сценария
     */
    public abstract void run();

    /**
     * @return представление сценария в виде строки
     */
    public String toString() {
        return _name;
    }
}

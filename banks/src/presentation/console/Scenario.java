package presentation.console;

/**
 * абстраутный класс сценария
 */
public abstract class Scenario
{
    protected final String name;

    protected Scenario(String name)
    {
        this.name = name;
    }

    /**
     * запуск сценария
     */
    public abstract void run();

    /**
     * @return представление сценария в виде строки
     */
    public String toString() {
        return name;
    }
}

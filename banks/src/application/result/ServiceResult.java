package application.result;

import lombok.Getter;

/**
 * Результат работы сервиса
 */
@Getter
public abstract class ServiceResult
{
    protected String _message;

    private ServiceResult()
    {
    }

    /**
     * Удачный результат
     */
    public static final class Success extends ServiceResult
    {
        public Success(String message)
        {
            _message = message;
        }
    }

    /**
     * Провальный результат
     */
    public static final class Failure extends ServiceResult
    {
        public Failure(String message)
        {
            _message = message;
        }
    }
}
package application.cotracts;

import lombok.Getter;

/**
 * результат входа в систему
 */
@Getter
public abstract class LoginResult {

    protected String message;
    private LoginResult() {}


    /**
     * Удачный результат
     */
    public static final class Success extends LoginResult {
        public Success() {
            message = "Success";
        }
    }

    /**
     * когда не найден аккаунт, в который пытаются войти
     */
    public static final class NotFound extends LoginResult {
        public NotFound() {
            message = "Not found";
        }
    }

    /**
     * ошибка входа в аккаунт
     */
    public static final class Failure extends LoginResult {
        public Failure() {
            message = "Failure";
        }
    }
}

package application.result;

import lombok.Getter;

@Getter
public abstract class ServiceResult {
    private final String message;

    private ServiceResult(String message) {
        this.message = message;
    }

    public static final class Success extends ServiceResult {
        public Success(String message) {
            super(message);
        }
    }

    public static final class Failure extends ServiceResult {
        public Failure(String message) {
            super(message);
        }
    }
}

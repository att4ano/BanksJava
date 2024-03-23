package domain.exceptions;

import org.jetbrains.annotations.NotNull;

public class DepositAccountException extends DomainException {
    private DepositAccountException(String message) {
        super(message);
    }

    public static @NotNull DepositAccountException termIsNotOver() {
        return new DepositAccountException("Term is not over");
    }
}

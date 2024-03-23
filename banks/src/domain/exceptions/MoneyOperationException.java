package domain.exceptions;

import org.jetbrains.annotations.NotNull;

public class MoneyOperationException extends DomainException{
    private MoneyOperationException(String message) {
        super(message);
    }

    public static @NotNull MoneyOperationException notEnoughMoney() {
        return new MoneyOperationException("Not enough money on balance");
    }
}

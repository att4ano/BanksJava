package domain.exceptions;

import org.jetbrains.annotations.NotNull;

public class NotFoundException extends DomainException{
    private NotFoundException(String message) {
        super(message);
    }

    public static @NotNull NotFoundException accountNotFound() {
        return new NotFoundException("Account not found");
    }

    public static @NotNull NotFoundException clientNotFound() {
        return new NotFoundException("Client not found");
    }

    public static @NotNull NotFoundException bankNotFound() {
        return new NotFoundException("Bank not found");
    }

    public static @NotNull NotFoundException transactionNotFound() {
        return new NotFoundException("Transaction not found");
    }
}

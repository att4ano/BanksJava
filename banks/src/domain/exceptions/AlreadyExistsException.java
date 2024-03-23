package domain.exceptions;

import org.jetbrains.annotations.NotNull;

public class AlreadyExistsException extends DomainException{
    private AlreadyExistsException(String message) {
        super(message);
    }

    public static @NotNull AlreadyExistsException accountAlreadyExists() {
        return new AlreadyExistsException("Account already exists");
    }

    public static @NotNull AlreadyExistsException clientAlreadyExists() {
        return new AlreadyExistsException("Client already exists");
    }

    public static @NotNull AlreadyExistsException bankAlreadyExists() {
        return new AlreadyExistsException("Bank already exists");
    }
}

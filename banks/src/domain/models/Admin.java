package domain.models;

import lombok.Data;

import java.util.UUID;

/**
 * Админ через которого происходит вход в центральный банк
 */
@Data
public class Admin {
    private final UUID _id;
    private final String _password;

    public Admin(UUID id, String password) {
        _id = id;
        _password = password;
    }
}

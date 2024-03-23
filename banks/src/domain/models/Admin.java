package domain.models;

import lombok.Data;

import java.util.UUID;

/**
 * Админ через которого происходит вход в центральный банк
 */
@Data
public class Admin {
    private final UUID id;
    private final String password;

    public Admin(UUID id, String password) {
        this.password = password;
        this.id = id;
    }
}

package application.application;

import domain.models.Admin;
import domain.models.Bank;
import domain.models.Client;
import lombok.Getter;

/**
 * конкртеная сессия за кого можно зарегаться
 */
public abstract class CurrentSession {

    private CurrentSession() {
    }

    /**
     * Сессия за клиента
     */
    //
    @Getter
    public static final class ClientSession extends CurrentSession {
        private final Client client;

        public ClientSession(Client client) {
            this.client = client;
        }

    }

    /**
     * Сессия за банк
     */
    @Getter
    public static final class BankSession extends CurrentSession {
        private final Bank bank;

        public BankSession(Bank bank) {
            this.bank = bank;
        }
    }

    /**
     * Сессия за центральный банк
     */
    public static final class CentralBankSession extends CurrentSession {
        private final Admin admin;

        public CentralBankSession(Admin admin) {
            this.admin = admin;
        }
    }


    /**
     * сессия не авторизованного пользователя
     */
    public static final class UnauthorizedSession extends CurrentSession {
    }
}

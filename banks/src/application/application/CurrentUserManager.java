package application.application;

import application.contracts.ICurrentUserManager;

/**
 * менеджер, который управляет сессией
 */
public class CurrentUserManager implements ICurrentUserManager {

    private CurrentSession currentSession;

    public CurrentUserManager() { }

    /**
     * @return возвращает данную сессию
     */
    @Override
    public CurrentSession getCurrentSession() {
        return currentSession;
    }

    /**
     * @param currentSession новая сессия которую надо поставить
     */
    @Override
    public void setCurrentSession(CurrentSession currentSession) {
        this.currentSession = currentSession;
    }
}


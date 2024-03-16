package application.application;

import application.contracts.ICurrentUserManager;

/**
 * менеджер, который управляет сессией
 */
public class CurrentUserManager implements ICurrentUserManager {

    private CurrentSession _currentSession;

    public CurrentUserManager() { }

    /**
     * @return возвращает данную сессию
     */
    @Override
    public CurrentSession getCurrentSession() {
        return _currentSession;
    }

    /**
     * @param currentSession новая сессия которую надо поставить
     */
    @Override
    public void setCurrentSession(CurrentSession currentSession) {
        _currentSession = currentSession;
    }
}


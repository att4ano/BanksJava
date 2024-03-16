package application.contracts;

import application.application.CurrentSession;

public interface ICurrentUserManager {
    CurrentSession getCurrentSession();
    void setCurrentSession(CurrentSession currentSession);
}

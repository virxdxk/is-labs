package ru.itmo.se.is.db;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.internal.sessions.DatabaseSessionImpl;
import org.eclipse.persistence.sessions.Project;

@ApplicationScoped
@NoArgsConstructor
public class EclipseLinkSessionProvider {
    @Inject
    private Project project;

    private DatabaseSessionImpl session;

    @Produces
    @ApplicationScoped
    public DatabaseSessionImpl createDatabaseSession() {
        if (session == null) {
            session = (DatabaseSessionImpl) project.createDatabaseSession();
            session.login();
        }
        return session;
    }

    @PreDestroy
    public void cleanup() {
        if (session != null && session.isLoggedIn()) {
            session.logout();
        }
    }
}

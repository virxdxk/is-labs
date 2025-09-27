package ru.itmo.se.is.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.platform.database.PostgreSQLPlatform;
import org.eclipse.persistence.sequencing.NativeSequence;
import org.eclipse.persistence.sessions.DatabaseLogin;

@ApplicationScoped
@NoArgsConstructor
public class DatabaseLoginConfig {

    @Produces
    @ApplicationScoped
    public DatabaseLogin createDatabaseLogin() {
        String dbUrl = "jdbc:postgresql://localhost:5432/testdb";
        String dbUser = "postgres";
        String dbPassword = "password";

        DatabaseLogin login = new DatabaseLogin();
        login.setPlatform(new PostgreSQLPlatform());
        login.setUserName(dbUser);
        login.setPassword(dbPassword);
        login.setConnectionString(dbUrl);
        login.setDefaultSequence(new NativeSequence());
        login.shouldUseNativeSequencing();
        return login;
    }
}

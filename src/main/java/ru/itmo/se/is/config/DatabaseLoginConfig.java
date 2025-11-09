package ru.itmo.se.is.config;

import org.eclipse.persistence.platform.database.PostgreSQLPlatform;
import org.eclipse.persistence.sequencing.NativeSequence;
import org.eclipse.persistence.sessions.DatabaseLogin;
import org.eclipse.persistence.sessions.JNDIConnector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
public class DatabaseLoginConfig {

    @Bean
    public DatabaseLogin databaseLogin() {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/jdbc/MyDS");

            JNDIConnector connector = new JNDIConnector();
            connector.setDataSource(ds);

            DatabaseLogin login = new DatabaseLogin();
            login.setPlatform(new PostgreSQLPlatform());
            login.setDefaultSequence(new NativeSequence());
            login.shouldUseNativeSequencing();
            login.setConnector(connector);

            return login;
        } catch (NamingException e) {
            throw new RuntimeException("Failed to lookup DataSource", e);
        }
    }
}

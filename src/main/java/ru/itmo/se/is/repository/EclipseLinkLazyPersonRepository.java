package ru.itmo.se.is.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.sessions.DatabaseSession;
import ru.itmo.se.is.entity.Person;

@ApplicationScoped
@NoArgsConstructor
public class EclipseLinkLazyPersonRepository extends EclipseLinkLazyRepository<Person> {
    @Inject
    public EclipseLinkLazyPersonRepository(DatabaseSession session) {
        super(Person.class, session);
    }
}

package ru.itmo.se.is.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.sessions.DatabaseSession;
import org.eclipse.persistence.sessions.UnitOfWork;
import ru.itmo.se.is.entity.Person;

@ApplicationScoped
@NoArgsConstructor
public class EclipseLinkPersonRepository extends GenericEclipseLinkRepository<Person, Long> {

    @Inject
    public EclipseLinkPersonRepository(DatabaseSession session) {
        super(Person.class, session);
    }

    @Override
    protected void registerNestedFields(UnitOfWork uow, Person entity) {
    }
}

package ru.itmo.se.is.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.eclipse.persistence.sessions.DatabaseSession;
import org.eclipse.persistence.sessions.UnitOfWork;
import org.springframework.stereotype.Repository;
import ru.itmo.se.is.entity.Person;

@org.springframework.stereotype.Repository
public class EclipseLinkPersonRepository extends GenericEclipseLinkRepository<Person, Long> {

    @Autowired
    public EclipseLinkPersonRepository(DatabaseSession session) {
        super(Person.class, session);
    }

    @Override
    protected void registerNestedFields(UnitOfWork uow, Person entity) {
    }
}

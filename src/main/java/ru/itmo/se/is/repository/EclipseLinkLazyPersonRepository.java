package ru.itmo.se.is.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.eclipse.persistence.sessions.DatabaseSession;
import org.springframework.stereotype.Repository;
import ru.itmo.se.is.entity.Person;

@org.springframework.stereotype.Repository
public class EclipseLinkLazyPersonRepository extends GenericEclipseLinkLazyRepository<Person> {
    @Autowired
    public EclipseLinkLazyPersonRepository(DatabaseSession session) {
        super(Person.class, session);
    }
}

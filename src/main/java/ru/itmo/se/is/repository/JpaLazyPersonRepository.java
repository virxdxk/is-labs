package ru.itmo.se.is.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.NoArgsConstructor;
import ru.itmo.se.is.entity.Person;

@NoArgsConstructor
@ApplicationScoped
public class JpaLazyPersonRepository extends JpaLazyRepository<Person> {

    @Inject
    public JpaLazyPersonRepository(EntityManager em) {
        super(Person.class, em);
    }
}

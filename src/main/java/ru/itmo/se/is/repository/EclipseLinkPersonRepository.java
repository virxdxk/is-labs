package ru.itmo.se.is.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.queries.ReadAllQuery;
import org.eclipse.persistence.queries.ReadObjectQuery;
import org.eclipse.persistence.sessions.DatabaseSession;
import org.eclipse.persistence.sessions.UnitOfWork;
import ru.itmo.se.is.entity.Person;

import java.util.List;

@ApplicationScoped
@NoArgsConstructor
public class EclipseLinkPersonRepository implements PersonRepository {

    @Inject
    private DatabaseSession session;

    @Override
    public List<Person> findAll() {
        ReadAllQuery query = new ReadAllQuery(Person.class);
        return (List<Person>) session.executeQuery(query);
    }

    @Override
    public Person findById(long id) {
        ReadObjectQuery query = new ReadObjectQuery(Person.class);
        query.setSelectionCriteria(
                new ExpressionBuilder().get("id").equal(id)
        );
        return (Person) session.executeQuery(query);
    }

    @Override
    public Person insert(Person person) {
        UnitOfWork uow = session.acquireUnitOfWork();
        Person merged = (Person) uow.registerObject(person);
        uow.commit();
        return merged;
    }

    @Override
    public Person update(long id, Person person) {
        UnitOfWork uow = session.acquireUnitOfWork();

        Person managed = (Person) uow.readObject(Person.class, new ExpressionBuilder().get("id").equal(id));

        managed.setName(person.getName());
        managed.setEyeColor(person.getEyeColor());
        managed.setHairColor(person.getHairColor());
        managed.setLocation(person.getLocation());
        managed.setNationality(person.getNationality());
        uow.commit();
        return managed;
    }

    @Override
    public void deleteById(long id) {
        UnitOfWork uow = session.acquireUnitOfWork();
        Person ref = (Person) uow.readObject(Person.class,
                new ExpressionBuilder().get("id").equal(id));
        if (ref != null) {
            uow.deleteObject(ref);
        }
        uow.commit();
    }
}

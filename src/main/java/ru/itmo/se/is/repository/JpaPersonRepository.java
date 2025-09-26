package ru.itmo.se.is.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ru.itmo.se.is.entity.Person;

import java.util.List;

@ApplicationScoped
public class JpaPersonRepository implements PersonRepository {
    @Inject
    private EntityManager em;

    @Override
    public List<Person> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        Root<Person> root = cq.from(Person.class);
        cq.select(root);
        return em.createQuery(cq).getResultList();
    }

    @Override
    public Person findById(long id) {
        return em.find(Person.class, id);
    }

    @Override
    public Person save(Person person) {
        return em.merge(person);
    }

    @Override
    public void deleteById(long id) {
        em.remove(em.getReference(Person.class, id));
    }
}

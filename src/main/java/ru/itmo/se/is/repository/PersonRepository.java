package ru.itmo.se.is.repository;

import ru.itmo.se.is.entity.Person;

import java.util.List;

public interface PersonRepository {
    List<Person> findAll();

    Person findById(long id);

    Person save(Person person);

    void deleteById(long id);
}

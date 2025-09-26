package ru.itmo.se.is.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class ResourcesConfig {

    @Produces
    @PersistenceContext
    @Dependent
    private EntityManager em;
}

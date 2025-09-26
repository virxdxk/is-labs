package ru.itmo.se.is.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import ru.itmo.se.is.entity.Movie;

import java.util.List;

@ApplicationScoped
public class JpaMovieRepository implements MovieRepository {
    @Inject
    private EntityManager em;

    public List<Movie> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> root = cq.from(Movie.class);
        cq.select(root);
        return em.createQuery(cq).getResultList();
    }

    public Movie findById(long id) {
        return em.find(Movie.class, id);
    }

    public Movie save(Movie movie) {
        return em.merge(movie);
    }

    public void deleteById(long id) {
        em.remove(em.getReference(Movie.class, id));
    }
}

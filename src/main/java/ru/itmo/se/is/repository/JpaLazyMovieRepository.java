package ru.itmo.se.is.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.NoArgsConstructor;
import ru.itmo.se.is.entity.Movie;

@ApplicationScoped
@NoArgsConstructor
public class JpaLazyMovieRepository extends JpaLazyRepository<Movie> {
    @Inject
    public JpaLazyMovieRepository(EntityManager em) {
        super(Movie.class, em);
    }
}

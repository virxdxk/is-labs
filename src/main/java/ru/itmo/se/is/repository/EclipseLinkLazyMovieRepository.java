package ru.itmo.se.is.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.eclipse.persistence.sessions.DatabaseSession;
import org.springframework.stereotype.Repository;
import ru.itmo.se.is.entity.Movie;

@org.springframework.stereotype.Repository
public class EclipseLinkLazyMovieRepository extends GenericEclipseLinkLazyRepository<Movie> {
    @Autowired
    public EclipseLinkLazyMovieRepository(DatabaseSession session) {
        super(Movie.class, session);
    }
}

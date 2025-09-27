package ru.itmo.se.is.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.sessions.DatabaseSession;
import ru.itmo.se.is.entity.Movie;

@ApplicationScoped
@NoArgsConstructor
public class EclipseLinkLazyMovieRepository extends GenericEclipseLinkLazyRepository<Movie> {
    @Inject
    public EclipseLinkLazyMovieRepository(DatabaseSession session) {
        super(Movie.class, session);
    }
}

package ru.itmo.se.is.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.sessions.DatabaseSession;
import org.eclipse.persistence.sessions.UnitOfWork;
import ru.itmo.se.is.entity.Movie;
import ru.itmo.se.is.entity.Person;

@ApplicationScoped
@NoArgsConstructor
public class EclipseLinkMovieRepository extends GenericEclipseLinkRepository<Movie, Long> {

    @Inject
    public EclipseLinkMovieRepository(DatabaseSession session) {
        super(Movie.class, session);
    }

    @Override
    protected void registerNestedFields(UnitOfWork uow, Movie movie) {
        if (movie.getDirector() != null) {
            movie.setDirector((Person) uow.registerExistingObject(movie.getDirector()));
        }
        if (movie.getOperator() != null) {
            movie.setOperator((Person) uow.registerExistingObject(movie.getOperator()));
        }
        if (movie.getScreenwriter() != null) {
            movie.setScreenwriter((Person) uow.registerExistingObject(movie.getScreenwriter()));
        }
    }
}

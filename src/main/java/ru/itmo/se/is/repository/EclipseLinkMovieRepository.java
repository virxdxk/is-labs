package ru.itmo.se.is.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.eclipse.persistence.sessions.DatabaseSession;
import org.eclipse.persistence.sessions.UnitOfWork;
import org.springframework.stereotype.Repository;
import ru.itmo.se.is.entity.Movie;
import ru.itmo.se.is.entity.Person;

@org.springframework.stereotype.Repository
public class EclipseLinkMovieRepository extends GenericEclipseLinkRepository<Movie, Long> {

    @Autowired
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

package ru.itmo.se.is.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.queries.ReadAllQuery;
import org.eclipse.persistence.queries.ReadObjectQuery;
import org.eclipse.persistence.sessions.DatabaseSession;
import org.eclipse.persistence.sessions.UnitOfWork;
import ru.itmo.se.is.entity.Movie;
import ru.itmo.se.is.entity.Person;

import java.time.ZonedDateTime;
import java.util.List;

@ApplicationScoped
@NoArgsConstructor
public class EclipseLinkMovieRepository implements MovieRepository {

    @Inject
    private DatabaseSession session;

    @Override
    public List<Movie> findAll() {
        ReadAllQuery query = new ReadAllQuery(Movie.class);
        return (List<Movie>) session.executeQuery(query);
    }

    @Override
    public Movie findById(long id) {
        ReadObjectQuery query = new ReadObjectQuery(Movie.class);
        query.setSelectionCriteria(
                new ExpressionBuilder().get("id").equal(id)
        );
        return (Movie) session.executeQuery(query);
    }

    @Override
    public Movie insert(Movie movie) {
        movie.setCreationDate(ZonedDateTime.now());
        UnitOfWork uow = session.acquireUnitOfWork();
        if (movie.getDirector() != null) {
            movie.setDirector((Person) uow.registerExistingObject(movie.getDirector()));
        }
        if (movie.getScreenwriter() != null) {
            movie.setScreenwriter((Person) uow.registerExistingObject(movie.getScreenwriter()));
        }
        if (movie.getOperator() != null) {
            movie.setOperator((Person) uow.registerExistingObject(movie.getOperator()));
        }
        Movie merged = (Movie) uow.registerNewObject(movie);
        uow.commit();
        return merged;
    }

    @Override
    public Movie update(long id, Movie movie) {
        UnitOfWork uow = session.acquireUnitOfWork();

        Movie managed = (Movie) uow.readObject(Movie.class, new ExpressionBuilder().get("id").equal(id));

        managed.setName(movie.getName());
        managed.setBudget(movie.getBudget());
        managed.setGenre(movie.getGenre());
        managed.setGoldenPalmCount(movie.getGoldenPalmCount());
        managed.setCoordinates(movie.getCoordinates());
        managed.setLength(movie.getLength());
        managed.setMpaaRating(movie.getMpaaRating());
        managed.setOscarsCount(movie.getOscarsCount());
        managed.setTagline(movie.getTagline());
        managed.setTotalBoxOffice(movie.getTotalBoxOffice());
        managed.setUsaBoxOffice(movie.getUsaBoxOffice());

        managed.setDirector((Person) uow.registerExistingObject(movie.getDirector()));
        managed.setOperator((Person) uow.registerExistingObject(movie.getOperator()));
        managed.setScreenwriter((Person) uow.registerExistingObject(movie.getScreenwriter()));

        uow.commit();
        return managed;
    }

    @Override
    public void deleteById(long id) {
        UnitOfWork uow = session.acquireUnitOfWork();
        Movie ref = (Movie) uow.readObject(Movie.class,
                new ExpressionBuilder().get("id").equal(id));
        if (ref != null) {
            uow.deleteObject(ref);
        }
        uow.commit();
    }
}

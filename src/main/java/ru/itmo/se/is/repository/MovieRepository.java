package ru.itmo.se.is.repository;

import ru.itmo.se.is.entity.Movie;

import java.util.List;

public interface MovieRepository {
    List<Movie> findAll();

    Movie findById(long id);

    Movie save(Movie movie);

    void deleteById(long id);
}

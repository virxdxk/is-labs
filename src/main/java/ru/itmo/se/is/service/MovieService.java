package ru.itmo.se.is.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import ru.itmo.se.is.dto.lazy.LazyBeanParamDto;
import ru.itmo.se.is.dto.movie.MovieCountResponseDto;
import ru.itmo.se.is.dto.movie.MovieLazyResponseDto;
import ru.itmo.se.is.dto.movie.MovieRequestDto;
import ru.itmo.se.is.dto.movie.MovieResponseDto;
import ru.itmo.se.is.dto.person.PersonResponseDto;
import ru.itmo.se.is.entity.Movie;
import ru.itmo.se.is.entity.Person;
import ru.itmo.se.is.entity.value.MpaaRating;
import ru.itmo.se.is.mapper.MovieMapper;
import ru.itmo.se.is.mapper.PersonMapper;
import ru.itmo.se.is.repository.EclipseLinkLazyMovieRepository;
import ru.itmo.se.is.repository.MovieRepository;

import java.util.List;
import java.util.Map;

@ApplicationScoped
@Transactional
public class MovieService {

    @Inject
    private MovieRepository repository;

    @Inject
    private EclipseLinkLazyMovieRepository lazyRepository;

    @Inject
    private MovieMapper mapper;

    @Inject
    private PersonMapper personMapper;

    public MovieResponseDto create(MovieRequestDto dto) {
        return mapper.toDto(
                repository.insert(
                        mapper.toMovie(dto)
                )
        );
    }

    public void update(long id, MovieRequestDto dto) {
        Movie movie = mapper.toMovie(dto);
        repository.update(id, movie);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public MovieLazyResponseDto lazyGet(LazyBeanParamDto lazyBeanParamDto, Map<String, String> filterBy) {
        List<Movie> data = lazyRepository.load(
                lazyBeanParamDto.getFirst(),
                lazyBeanParamDto.getPageSize(),
                lazyBeanParamDto.getSortField(),
                lazyBeanParamDto.getSortOrder(),
                filterBy
        );
        long totalRecords = lazyRepository.count(filterBy);
        return new MovieLazyResponseDto(mapper.toDto(data), totalRecords);
    }

    public MovieCountResponseDto countByTagline(String tagline) {
        Long count = repository.findAll().stream()
                .map(Movie::getTagline)
                .filter(t -> t.equals(tagline))
                .count();
        return new MovieCountResponseDto(count);
    }

    public MovieCountResponseDto countLessThanGoldenPalm(long baseCount) {
        Long count = repository.findAll().stream()
                .map(Movie::getGoldenPalmCount)
                .filter(c -> c < baseCount)
                .count();
        return new MovieCountResponseDto(count);
    }

    public MovieCountResponseDto countGreaterThanGoldenPalm(long baseCount) {
        Long count = repository.findAll().stream()
                .map(Movie::getGoldenPalmCount)
                .filter(c -> c > baseCount)
                .count();
        return new MovieCountResponseDto(count);
    }

    public List<PersonResponseDto> getDirectorsWithoutOscars() {
        List<Movie> movies = repository.findAll();
        List<Person> directors = movies.stream()
                .filter(m -> m.getOscarsCount() == 0)
                .map(Movie::getDirector)
                .filter(d -> movies.stream()
                        .filter(m -> m.getDirector().equals(d))
                        .allMatch(m -> m.getOscarsCount() == 0))
                .distinct()
                .toList();
        return personMapper.toDto(directors);
    }

    public void addOscarToRated() {
        repository.findAll().stream()
                .filter(m -> m.getMpaaRating().equals(MpaaRating.R))
                .forEach(m -> {
                    m.setOscarsCount(m.getOscarsCount() + 1);
                    repository.insert(m);
                });
    }
}

package ru.itmo.se.is.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.itmo.se.is.dto.movie.MovieLazyBeanParamDto;
import ru.itmo.se.is.dto.movie.MovieRequestDto;
import ru.itmo.se.is.dto.movie.MovieResponseDto;
import ru.itmo.se.is.service.MovieService;
import ru.itmo.se.is.websocket.WebSocketEndpoint;
import ru.itmo.se.is.websocket.WebSocketMessageType;

import java.net.URI;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping
    public ResponseEntity<?> getAllMovies(@Valid @ModelAttribute MovieLazyBeanParamDto lazyBeanParamDto) {
        return ResponseEntity.ok(service.lazyGet(lazyBeanParamDto));
    }

    @PostMapping
    public ResponseEntity<MovieResponseDto> createMovie(@Valid @RequestBody MovieRequestDto dto) {
        MovieResponseDto createdMovie = service.create(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdMovie.getId())
                .toUri();

        WebSocketEndpoint.broadcast(WebSocketMessageType.MOVIE);

        return ResponseEntity.created(location).body(createdMovie);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateMovie(@PathVariable("id") long id, @Valid @RequestBody MovieRequestDto dto) {
        service.update(id, dto);
        WebSocketEndpoint.broadcast(WebSocketMessageType.MOVIE);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("id") long id) {
        service.delete(id);
        WebSocketEndpoint.broadcast(WebSocketMessageType.MOVIE);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count-by-tagline/{tagline}")
    public ResponseEntity<?> countByTagline(@PathVariable("tagline") String tagline) {
        return ResponseEntity.ok(service.countByTagline(tagline));
    }

    @GetMapping("/count-less-than-golden-palm/{count}")
    public ResponseEntity<?> countLessThanGoldenPalm(@PathVariable("count") long count) {
        return ResponseEntity.ok(service.countLessThanGoldenPalm(count));
    }

    @GetMapping("/count-greater-than-golden-palm/{count}")
    public ResponseEntity<?> countGreaterThanGoldenPalm(@PathVariable("count") long count) {
        return ResponseEntity.ok(service.countGreaterThanGoldenPalm(count));
    }

    @GetMapping("/directors-without-oscars")
    public ResponseEntity<?> getDirectorsWithoutOscars() {
        return ResponseEntity.ok(service.getDirectorsWithoutOscars());
    }

    @PatchMapping("/add-oscar-to-r-rated")
    public ResponseEntity<Void> addOscarToRated() {
        service.addOscarToRated();
        WebSocketEndpoint.broadcast(WebSocketMessageType.MOVIE);
        return ResponseEntity.noContent().build();
    }
}

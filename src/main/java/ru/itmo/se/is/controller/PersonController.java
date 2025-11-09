package ru.itmo.se.is.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.itmo.se.is.dto.person.PersonLazyBeanParamDto;
import ru.itmo.se.is.dto.person.PersonRequestDto;
import ru.itmo.se.is.dto.person.PersonResponseDto;
import ru.itmo.se.is.service.PersonService;
import ru.itmo.se.is.websocket.WebSocketEndpoint;
import ru.itmo.se.is.websocket.WebSocketMessageType;

import java.net.URI;

@RestController
@RequestMapping("/people")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping
    public ResponseEntity<?> getAllPeople(@Valid @ModelAttribute PersonLazyBeanParamDto lazyBeanParamDto) {
        return ResponseEntity.ok(service.lazyGet(lazyBeanParamDto));
    }

    @PostMapping
    public ResponseEntity<PersonResponseDto> createPerson(@Valid @RequestBody PersonRequestDto dto) {
        PersonResponseDto createdPerson = service.create(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPerson.getId())
                .toUri();
        
        WebSocketEndpoint.broadcast(WebSocketMessageType.PERSON);
        return ResponseEntity.created(location).body(createdPerson);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePerson(@PathVariable("id") long id, @Valid @RequestBody PersonRequestDto dto) {
        service.update(id, dto);
        WebSocketEndpoint.broadcast(WebSocketMessageType.PERSON);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable("id") long id) {
        service.delete(id);
        WebSocketEndpoint.broadcast(WebSocketMessageType.PERSON);
        return ResponseEntity.noContent().build();
    }
}

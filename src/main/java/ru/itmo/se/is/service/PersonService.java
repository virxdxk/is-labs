package ru.itmo.se.is.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import ru.itmo.se.is.dto.lazy.LazyBeanParamDto;
import ru.itmo.se.is.dto.person.PersonLazyResponseDto;
import ru.itmo.se.is.dto.person.PersonRequestDto;
import ru.itmo.se.is.dto.person.PersonResponseDto;
import ru.itmo.se.is.entity.Person;
import ru.itmo.se.is.mapper.PersonMapper;
import ru.itmo.se.is.repository.EclipseLinkLazyPersonRepository;
import ru.itmo.se.is.repository.PersonRepository;

import java.util.List;
import java.util.Map;

@ApplicationScoped
@Transactional
public class PersonService {
    @Inject
    private PersonRepository repository;

    @Inject
    private EclipseLinkLazyPersonRepository lazyRepository;

    @Inject
    private PersonMapper mapper;

    public PersonResponseDto create(PersonRequestDto dto) {
        return mapper.toDto(
                repository.insert(
                        mapper.toPerson(dto)
                )
        );
    }

    public void update(long id, PersonRequestDto dto) {
        Person person = mapper.toPerson(dto);
        repository.update(id, person);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public PersonLazyResponseDto lazyGet(LazyBeanParamDto lazyBeanParamDto, Map<String, String> filterBy) {
        List<Person> data = lazyRepository.load(
                lazyBeanParamDto.getFirst(),
                lazyBeanParamDto.getPageSize(),
                lazyBeanParamDto.getSortField(),
                lazyBeanParamDto.getSortOrder(),
                filterBy
        );
        long totalRecords = lazyRepository.count(filterBy);
        return new PersonLazyResponseDto(mapper.toDto(data), totalRecords);
    }

    public Person getPersonById(long id) {
        return repository.findById(id);
    }
}

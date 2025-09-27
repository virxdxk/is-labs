package ru.itmo.se.is.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.itmo.se.is.config.MapperConfig;
import ru.itmo.se.is.dto.person.PersonRequestDto;
import ru.itmo.se.is.dto.person.PersonResponseDto;
import ru.itmo.se.is.entity.Person;

import java.util.List;

@Mapper(config = MapperConfig.class)
public interface PersonMapper {
    @Mapping(target = "id", ignore = true)
    Person toPerson(PersonRequestDto dto);

    @Mapping(target = "id", ignore = true)
    void toPerson(PersonRequestDto dto, @MappingTarget Person entity);

    PersonResponseDto toDto(Person person);

    List<PersonResponseDto> toDto(List<Person> persons);
}

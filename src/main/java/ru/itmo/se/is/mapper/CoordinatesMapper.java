package ru.itmo.se.is.mapper;

import org.mapstruct.Mapper;
import ru.itmo.se.is.config.MapperConfig;
import ru.itmo.se.is.dto.coordinates.CoordinatesRequestDto;
import ru.itmo.se.is.dto.coordinates.CoordinatesResponseDto;
import ru.itmo.se.is.entity.Coordinates;

@Mapper(config =  MapperConfig.class)
public interface CoordinatesMapper {
    Coordinates toCoordinates(CoordinatesRequestDto dto);

    CoordinatesResponseDto toDto(Coordinates coordinates);
}

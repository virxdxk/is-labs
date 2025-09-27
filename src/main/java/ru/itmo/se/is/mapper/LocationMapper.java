package ru.itmo.se.is.mapper;

import org.mapstruct.Mapper;
import ru.itmo.se.is.config.MapperConfig;
import ru.itmo.se.is.dto.location.LocationRequestDto;
import ru.itmo.se.is.dto.location.LocationResponseDto;
import ru.itmo.se.is.entity.Location;

@Mapper(config = MapperConfig.class)
public interface LocationMapper {
    Location toLocation(LocationRequestDto dto);

    LocationResponseDto toDto(Location location);
}

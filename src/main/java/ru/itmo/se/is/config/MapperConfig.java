package ru.itmo.se.is.config;

import org.mapstruct.ReportingPolicy;

@org.mapstruct.MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        typeConversionPolicy = ReportingPolicy.ERROR
)
public interface MapperConfig {
}

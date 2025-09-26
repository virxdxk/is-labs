package ru.itmo.se.is.config;

import org.mapstruct.ReportingPolicy;

@org.mapstruct.MapperConfig(
        componentModel = "cdi",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        typeConversionPolicy = ReportingPolicy.ERROR
)
public interface MapperConfig {
}

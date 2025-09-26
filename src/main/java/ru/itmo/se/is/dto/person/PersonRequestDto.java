package ru.itmo.se.is.dto.person;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itmo.se.is.dto.location.LocationRequestDto;
import ru.itmo.se.is.entity.value.Color;
import ru.itmo.se.is.entity.value.Country;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequestDto implements Serializable {
    @NotBlank
    private String name;

    @Nullable
    private Color eyeColor;

    @NotNull
    private Color hairColor;

    @NotNull
    @Valid
    private LocationRequestDto location;

    @NotNull
    @Positive
    private Float weight;

    @Nullable
    private Country nationality;
}

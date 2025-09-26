package ru.itmo.se.is.dto.movie;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itmo.se.is.dto.coordinates.CoordinatesRequestDto;
import ru.itmo.se.is.entity.value.MovieGenre;
import ru.itmo.se.is.entity.value.MpaaRating;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequestDto implements Serializable {
    @NotBlank
    private String name;

    @NotNull
    @Valid
    private CoordinatesRequestDto coordinates;

    @NotNull
    @Positive
    private Integer oscarsCount;

    @NotNull
    @Positive
    private float budget;

    @NotNull
    @Positive
    private Integer totalBoxOffice;

    @Nullable
    private MpaaRating mpaaRating;

    @NotNull
    private Long directorId;

    @Nullable
    private Long screenwriterId;

    @Nullable
    private Long operatorId;

    @Nullable
    @Positive
    private Integer length;

    @NotNull
    @Positive
    private Integer goldenPalmCount;

    @NotNull
    @Positive
    private Integer usaBoxOffice;

    @NotNull
    private String tagline;

    @Nullable
    private MovieGenre genre;
}

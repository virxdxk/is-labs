package ru.itmo.se.is.dto.movie;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itmo.se.is.annotation.AllowedValues;
import ru.itmo.se.is.entity.value.MovieGenre;
import ru.itmo.se.is.entity.value.MpaaRating;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieLazyBeanParamDto implements Serializable {
    @Min(0)
    private int first = 0;

    private int pageSize = 10;

    @AllowedValues({"id", "name", "creationDate", "oscarsCount", "mpaaRating", "budget", "totalBoxOffice", "length", "goldenPalmCount", "usaBoxOffice", "tagline", "genre"})
    private String sortField = "id";

    @AllowedValues({"-1", "1"})
    private int sortOrder = 1;

    private Long idFilter;

    private String nameFilter;

    private MovieGenre genreFilter;

    private MpaaRating mpaaRatingFilter;

    private String taglineFilter;
}

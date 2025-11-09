package ru.itmo.se.is.dto.person;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itmo.se.is.annotation.AllowedValues;
import ru.itmo.se.is.entity.value.Color;
import ru.itmo.se.is.entity.value.Country;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonLazyBeanParamDto implements Serializable {
    @Min(0)
    private int first = 0;

    private int pageSize = 10;

    @AllowedValues({"name", "eyeColor", "hairColor", "weight", "nationality"})
    private String sortField = "name";

    @AllowedValues({"-1", "1"})
    private int sortOrder = 1;

    private String nameFilter;

    private Color eyeColorFilter;

    private Color hairColorFilter;

    private Country nationalityFilter;
}

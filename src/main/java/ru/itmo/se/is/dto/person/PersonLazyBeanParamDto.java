package ru.itmo.se.is.dto.person;

import jakarta.validation.constraints.Min;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
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
    @DefaultValue("0")
    @QueryParam("first")
    private int first;

    @DefaultValue("10")
    @QueryParam("pageSize")
    private int pageSize;

    @AllowedValues({"name", "eyeColor", "hairColor", "weight", "nationality"})
    @DefaultValue("name")
    @QueryParam("sortField")
    private String sortField;

    @AllowedValues({"-1", "1"})
    @DefaultValue("1")
    @QueryParam("sortOrder")
    private int sortOrder;

    @QueryParam("name")
    private String nameFilter;

    @QueryParam("eyeColor")
    private Color eyeColorFilter;

    @QueryParam("hairColor")
    private Color hairColorFilter;

    @QueryParam("nationality")
    private Country nationalityFilter;
}

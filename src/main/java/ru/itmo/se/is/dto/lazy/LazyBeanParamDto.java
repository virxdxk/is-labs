package ru.itmo.se.is.dto.lazy;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LazyBeanParamDto implements Serializable {
    @DefaultValue("0")
    @QueryParam("first")
    private int first;

    @DefaultValue("10")
    @QueryParam("pageSize")
    private int pageSize;

    @DefaultValue("")
    @QueryParam("sortField")
    private String sortField;

    @DefaultValue("1")
    @QueryParam("sortOrder")
    private int sortOrder;
}

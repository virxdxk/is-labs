package ru.itmo.se.is.dto.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponseDto implements Serializable {
    private Float x;
    private Double y;
    private Double z;
}

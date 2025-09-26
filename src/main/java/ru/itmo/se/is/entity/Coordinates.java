package ru.itmo.se.is.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Embeddable
public class Coordinates {

    @Column(nullable = false)
    private Double x;

    @Column(nullable = false)
    private long y;
}

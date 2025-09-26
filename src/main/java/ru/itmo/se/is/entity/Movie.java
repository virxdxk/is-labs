package ru.itmo.se.is.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itmo.se.is.entity.value.MovieGenre;
import ru.itmo.se.is.entity.value.MpaaRating;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Coordinates coordinates;

    @Column(name = "creation_data", nullable = false, updatable = false)
    private ZonedDateTime creationDate;

    @Column(nullable = false)
    private int oscarsCount;

    @Column(nullable = false)
    private float budget;

    @Column(name = "total_box_office", nullable = false)
    private int totalBoxOffice;

    @Enumerated(EnumType.STRING)
    @Column(name = "mpaa_rating")
    private MpaaRating mpaaRating;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "director_id", nullable = false)
    private Person director;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "screenwriter_id")
    private Person screenwriter;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "operator_id")
    private Person operator;

    @Column(check = @CheckConstraint(constraint = "INTEGER CHECK (length IS NULL OR length > 0)"))
    private Integer length;

    @Column(name = "golden_palm_count", nullable = false)
    private Integer goldenPalmCount;

    @Column(name = "usa_box_office", nullable = false)
    private int usaBoxOffice;

    @Column(nullable = false)
    private String tagline;

    @Enumerated(EnumType.STRING)
    @Column
    private MovieGenre genre;

    @PrePersist
    public void prePersist() {
        this.creationDate = ZonedDateTime.now();
    }
}

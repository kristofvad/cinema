package hu.nye.progkor.cinema.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Movie details.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class Movie {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer releaseYear;

    private Integer time; //min

    private MovieType suggestedMovieType;

    private String description;
}

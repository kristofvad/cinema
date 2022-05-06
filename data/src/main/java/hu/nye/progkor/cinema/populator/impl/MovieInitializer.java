package hu.nye.progkor.cinema.populator.impl;

import hu.nye.progkor.cinema.model.Movie;
import hu.nye.progkor.cinema.model.MovieType;
import hu.nye.progkor.cinema.populator.DBPopulator;

import java.util.List;

import hu.nye.progkor.cinema.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Order(1)
public class MovieInitializer implements DBPopulator {

    private static final List<Movie> MOVIES = List.of(
            new Movie(1L, "Pulp Fiction", 1994, 154, MovieType.MOVIE_2D, "The path of the righteous man is beset of all sides by the iniquities of the selfish and the tyranny of evil me"),
            new Movie(2L, "Spider-Man", 2002, 121, MovieType.BOTH, "With great power comes great responsibility"),
            new Movie(3L, "Ready Player One", 2018, 140, MovieType.BOTH, "First to the key"),
            new Movie(4L, "Avengers: Infinity War", 2018, 149, MovieType.BOTH, "Bring me Thanos"),
            new Movie(5L, "Avengers: Endgame", 2019, 182, MovieType.BOTH, "Avengers Assemble"),
            new Movie(6L, "SpiderMan: Into the Spiderverse", 2018, 116, MovieType.MOVIE_3D, "Hey!"),
            new Movie(7L, "Django Unchained", 2012, 165, MovieType.MOVIE_2D, "I like the way you die boy"),
            new Movie(8L, "Star Wars: A New Hope", 1977, 121, MovieType.MOVIE_2D, "May the force be with you")

    );

    private final MovieRepository movieRepository;

    public MovieInitializer(final MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void populateDatabase() {
        log.info("Initialize Movies...");
        movieRepository.saveAll(MOVIES);
        log.info("Finished initialization of Movies.");
    }
}

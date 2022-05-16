package hu.nye.progkor.cinema.service;

import java.util.List;

import hu.nye.progkor.cinema.model.dto.MovieDTO;

/**
 * MovieService interface.
 */
public interface MovieService {

    List<MovieDTO> getAllMovies();

    MovieDTO getMovie(Long id);

    MovieDTO createMovie(MovieDTO movie);

    MovieDTO updateMovie(Long id, MovieDTO movieChanges);

    void deleteMovie(Long id);

}

package hu.nye.progkor.cinema.service;

import hu.nye.progkor.cinema.model.dto.MovieDTO;
import java.util.List;

public interface MovieService {

    List<MovieDTO> getAllMovies();

    MovieDTO getMovie(Long id);

    MovieDTO createMovie(MovieDTO movie);

    MovieDTO updateMovie(Long id, MovieDTO movieChanges);

    void deleteMovie(Long id);

}

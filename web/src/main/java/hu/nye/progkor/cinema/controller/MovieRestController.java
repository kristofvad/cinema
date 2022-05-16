package hu.nye.progkor.cinema.controller;

import java.util.List;

import hu.nye.progkor.cinema.model.dto.MovieDTO;
import hu.nye.progkor.cinema.model.request.MovieRequest;
import hu.nye.progkor.cinema.model.response.MovieResponse;
import hu.nye.progkor.cinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Javadoc comment.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
@Log4j2
public class MovieRestController {

    private final MovieService movieService;
    private final Converter<MovieDTO, MovieResponse> movieDTOMovieResponseConverter;
    private final Converter<MovieRequest, MovieDTO> movieRequestMovieDTOConverter;

    /**
     * Javadoc comment.
     */
    @GetMapping
    public List<MovieResponse> getMovies() {
        log.info("Get all Movies");
        return movieService.getAllMovies().stream()
                .map(movieDTOMovieResponseConverter::convert)
                .toList();
    }

    /**
     * Javadoc comment.
     */
    @GetMapping("/{id}")
    public MovieResponse getMovie(final @PathVariable Long id) {
        log.info("Get a Movie, ID:{}", id);
        return movieDTOMovieResponseConverter.convert(movieService.getMovie(id));
    }

    /**
     * Javadoc comment.
     */
    @PostMapping
    public MovieResponse createMovie(final @RequestBody MovieRequest movieRequest) {
        log.info("Create a new Movie, details: {}", movieRequest);
        return movieDTOMovieResponseConverter.convert(
                movieService.createMovie(movieRequestMovieDTOConverter.convert(movieRequest))
        );
    }

    /**
     * Javadoc comment.
     */
    @PutMapping("/{id}")
    public MovieResponse updateMovie(final @PathVariable Long id, final @RequestBody MovieRequest movieRequest) {
        log.info("Update a Movie with Id:{} to Movie:{}", id, movieRequest);
        return movieDTOMovieResponseConverter.convert(
                movieService.updateMovie(id, movieRequestMovieDTOConverter.convert(movieRequest))
        );
    }

    /**
     * Javadoc comment.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(final @PathVariable Long id) {
        log.info("Delete a Movie with ID:{}", id);
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


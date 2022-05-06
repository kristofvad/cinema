package hu.nye.progkor.cinema.controller;

import hu.nye.progkor.cinema.model.dto.MovieDTO;
import hu.nye.progkor.cinema.model.request.MovieRequest;
import hu.nye.progkor.cinema.model.response.MovieResponse;
import hu.nye.progkor.cinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
@Log4j2
public class MovieRestController {

    private final MovieService movieService;
    private final Converter<MovieDTO, MovieResponse> movieDTOMovieResponseConverter;
    private final Converter<MovieRequest, MovieDTO> movieRequestMovieDTOConverter;

    @GetMapping
    public List<MovieResponse> getMovies() {
        log.info("Get all Movies");
        return movieService.getAllMovies().stream()
                .map(movieDTOMovieResponseConverter::convert)
                .toList();
    }

    @GetMapping("/{id}")
    public MovieResponse getMovie(final @PathVariable Long id) {
        log.info("Get a Movie, ID:{}", id);
        return movieDTOMovieResponseConverter.convert(movieService.getMovie(id));
    }

    @PostMapping
    public MovieResponse createMovie(final @RequestBody MovieRequest movieRequest) {
        log.info("Create a new Movie, details: {}", movieRequest);
        return movieDTOMovieResponseConverter.convert(
                movieService.createMovie(movieRequestMovieDTOConverter.convert(movieRequest))
        );
    }

    @PutMapping("/{id}")
    public MovieResponse updateMovie(final @PathVariable Long id, final @RequestBody MovieRequest movieRequest) {
        log.info("Update a Movie with Id:{} to Movie:{}", id, movieRequest);
        return movieDTOMovieResponseConverter.convert(
                movieService.updateMovie(id, movieRequestMovieDTOConverter.convert(movieRequest))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(final @PathVariable Long id) {
        log.info("Delete a Movie with ID:{}", id);
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


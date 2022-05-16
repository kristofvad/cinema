package hu.nye.progkor.cinema.service.impl;

import java.util.List;
import java.util.Optional;

import hu.nye.progkor.cinema.model.Movie;
import hu.nye.progkor.cinema.model.dto.MovieDTO;
import hu.nye.progkor.cinema.model.exception.NotFoundException;
import hu.nye.progkor.cinema.repository.MovieRepository;
import hu.nye.progkor.cinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

/**
 * MovieServiceImpl.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final Converter<MovieDTO, Movie> movieDtoToEntityConverter;
    private final Converter<Movie, MovieDTO> movieEntityToDtoConverter;

    @Override
    public List<MovieDTO> getAllMovies() {
        log.info("Get all Movies.");
        return movieRepository.findAll().stream()
                .map(movieEntityToDtoConverter::convert)
                .toList();
    }

    @Override
    public MovieDTO getMovie(final Long id) {
        log.info("Get a Movie with ID:{}.", id);
        return movieRepository.findById(id)
                .map(movieEntityToDtoConverter::convert)
                .orElseThrow(() -> new NotFoundException("There is movie with ID:" + id));
    }

    @Override
    public MovieDTO createMovie(final MovieDTO movie) {
        log.info("Create a Movie:{}.", movie);
        return Optional.ofNullable(movie)
                .map(movieDtoToEntityConverter::convert)
                .map(movieRepository::save)
                .map(movieEntityToDtoConverter::convert)
                .orElseThrow(() -> new IllegalArgumentException("Provided parameter is invalid: " + movie));
    }

    @Override
    public MovieDTO updateMovie(final Long id, final MovieDTO movieChanges) {
        log.info("Update Movie with ID:{} to {}.", id, movieChanges);
        final Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no Movie with ID:" + id));
        movie.setName(movieChanges.name());
        movie.setDescription(movieChanges.description());
        movie.setReleaseYear(movieChanges.releaseYear());
        movie.setTime(movieChanges.time());
        movie.setSuggestedMovieType(movieChanges.movieType());
        final Movie updatedMovie = movieRepository.save(movie);
        return movieEntityToDtoConverter.convert(updatedMovie);
    }

    @Override
    public void deleteMovie(final Long id) {
        log.info("Delete Movie with ID:{}.", id);
        movieRepository.deleteById(id);
    }
}
